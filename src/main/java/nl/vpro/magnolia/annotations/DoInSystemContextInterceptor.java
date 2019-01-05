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


    static ThreadLocal<State> THREAD_STATE = ThreadLocal.withInitial(State::new);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MgnlSystemContext annotation = invocation.getMethod().getAnnotation(MgnlSystemContext.class);
        if (annotation == null) {
            annotation = invocation.getMethod().getDeclaringClass().getAnnotation(MgnlSystemContext.class);
        }
        boolean releaseAfterExecution = true;
        if (annotation == null) {
            log.warn("Annotation not found on {}", invocation.getMethod());
        } else {
            releaseAfterExecution = annotation.releaseAfterExecution();
        }

        final State state = THREAD_STATE.get();
        state.begin();
        try {
            return MgnlContext.doInSystemContext(() -> {
                state.context = MgnlContext.getInstance();
                return invocation.proceed();
            }, false);
        } finally {
           state.end(releaseAfterExecution);
        }

    }
}
