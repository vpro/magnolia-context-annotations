package nl.vpro.magnolia.annotations;

import info.magnolia.context.MgnlContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
public class DoInSystemContextInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        MgnlSystemContext annotation = invocation.getMethod().getAnnotation(MgnlSystemContext.class);
        boolean requiresNew = annotation.requiresNew;
        return MgnlContext.doInSystemContext(new MgnlContext.Op<Object, Throwable>() {
            @Override
            public Object exec() throws Throwable {
                return invocation.proceed();

            }
        });


    }
}
