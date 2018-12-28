package nl.vpro.magnolia.annotations;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.module.site.Site;
import info.magnolia.module.site.SiteManager;
import info.magnolia.objectfactory.Components;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import com.google.common.annotations.Beta;

import nl.vpro.magnolia.SystemWebContext;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class DoInSystemContextInterceptor implements MethodInterceptor {


    static ThreadLocal<AtomicInteger> THREAD_COUNT = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MgnlSystemContext annotation = invocation.getMethod().getAnnotation(MgnlSystemContext.class);
        if (annotation == null) {
            annotation = invocation.getMethod().getDeclaringClass().getAnnotation(MgnlSystemContext.class);
        }
        boolean releaseAfterExecution = true;
        String site = "";
        if (annotation == null) {
            log.warn("Annotation not found on {}", invocation.getMethod());
        } else {
            releaseAfterExecution = annotation.releaseAfterExecution();
            site = annotation.site();
        }

        AtomicInteger count = THREAD_COUNT.get();
        count.incrementAndGet();
        try {
            if (StringUtils.isNotBlank(site)) {
                return doInWebContext(site, invocation);
            } else {
                return MgnlContext.doInSystemContext(invocation::proceed, false);
            }
        } finally {
            int newCount = count.decrementAndGet();
            if (newCount == 0) {
                if (releaseAfterExecution) {
                    MgnlContext.release();

                }
            }
        }

    }

    @Beta
    public static <V> V doInWebContext(String site, Callable<V> callable) throws Exception {
         Site siteObject = Components.getComponent(SiteManager.class).getSite(site);
        if (siteObject == null) {
            throw new IllegalArgumentException("No such site " + site);
        }
        Context before = MgnlContext.hasInstance() ? MgnlContext.getInstance() : null;

        try {
            MgnlContext.setInstance(new SystemWebContext(siteObject));
            return callable.call();
        } finally {
            MgnlContext.setInstance(before);
        }
    }

    protected static Object doInWebContext(String site, MethodInvocation invocation) throws Exception {
        return doInWebContext(site, () -> {
            try {
                return invocation.proceed();
            } catch (Exception e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }



}
