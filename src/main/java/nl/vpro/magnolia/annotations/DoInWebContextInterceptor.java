package nl.vpro.magnolia.annotations;

import com.google.common.annotations.Beta;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.module.site.Site;
import info.magnolia.module.site.SiteManager;
import info.magnolia.objectfactory.Components;
import lombok.extern.slf4j.Slf4j;
import nl.vpro.magnolia.SystemWebContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.Callable;

/**
 * @author Michiel Meeuwissen
 * @since 1.1
 */
@Slf4j
public class DoInWebContextInterceptor implements MethodInterceptor {


    static ThreadLocal<State> THREAD_STATE = ThreadLocal.withInitial(State::new);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MgnlWebContext annotation = invocation.getMethod().getAnnotation(MgnlWebContext.class);
        if (annotation == null) {
            annotation = invocation.getMethod().getDeclaringClass().getAnnotation(MgnlWebContext.class);
        }
        ReleaseAfterExecution releaseAfterExecution = ReleaseAfterExecution.SMART;
        String site = "";
        if (annotation == null) {
            log.warn("Annotation not found on {}", invocation.getMethod());
        } else {
            releaseAfterExecution = annotation.releaseAfterExecution();
            site = annotation.site();
        }

        final State state = THREAD_STATE.get();
        state.begin();

        try {
            return doInWebContext(site, invocation);
        } finally {
            state.end(releaseAfterExecution);
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
            THREAD_STATE.get().context = MgnlContext.getInstance();
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
