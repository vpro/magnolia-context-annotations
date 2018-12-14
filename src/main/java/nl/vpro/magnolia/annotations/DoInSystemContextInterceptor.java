package nl.vpro.magnolia.annotations;

import info.magnolia.context.MgnlContext;
import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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
        if (annotation == null) {
            log.warn("Annotation not found on {}", invocation);
        }
        return MgnlContext.doInSystemContext(new MgnlContext.Op<Object, Throwable>() {
            @Override
            public Object exec() throws Throwable {
                return invocation.proceed();

            }
        });


    }
}
