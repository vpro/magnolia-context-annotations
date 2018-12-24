package nl.vpro.magnolia.annotations;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.module.site.Site;
import info.magnolia.module.site.SiteManager;
import info.magnolia.objectfactory.Components;
import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import nl.vpro.magnolia.SystemWebContext;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class DoInSystemContextInterceptor implements MethodInterceptor {

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
        releaseAfterExecution &= ! MgnlContext.isSystemInstance();
        if (StringUtils.isNotBlank(site)) {
            Site siteObject = Components.getComponent(SiteManager.class).getSite(site);
            if (siteObject == null) {
                throw new IllegalArgumentException("No such site " + site);
            }
            Context before = MgnlContext.hasInstance() ? MgnlContext.getInstance() : null;

            try {
                MgnlContext.setInstance(new SystemWebContext(siteObject));
                return invocation.proceed();
            } finally {
                MgnlContext.setInstance(before);
            }

        } else {
            return MgnlContext.doInSystemContext(invocation::proceed, releaseAfterExecution);
        }

    }
}
