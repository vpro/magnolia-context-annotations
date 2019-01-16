package nl.vpro.magnolia.annotations;

import info.magnolia.context.MgnlContext;
import info.magnolia.context.SystemContext;
import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class DoInSystemContextInterceptor implements MethodInterceptor {


    static ThreadLocal<State<SystemContext>> THREAD_STATE = ThreadLocal.withInitial(State::new);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MgnlSystemContext annotation = invocation.getMethod().getAnnotation(MgnlSystemContext.class);
        if (annotation == null) {
            annotation = invocation.getMethod().getDeclaringClass().getAnnotation(MgnlSystemContext.class);
        }
        ReleaseAfterExecution releaseAfterExecution = ReleaseAfterExecution.SMART;
        if (annotation == null) {
            log.warn("Annotation not found on {}", invocation.getMethod());
        } else {
            releaseAfterExecution = annotation.releaseAfterExecution();
        }

        final State<SystemContext> state = THREAD_STATE.get();
        state.begin();
        try {
            return MgnlContext.doInSystemContext(() -> {
                state.context = (SystemContext) MgnlContext.getInstance();
                return invocation.proceed();
            }, false);
        } finally {
            state.end(releaseAfterExecution);
        }

    }
}
