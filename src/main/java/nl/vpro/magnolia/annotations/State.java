package nl.vpro.magnolia.annotations;

import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michiel Meeuwissen
 * @since 1.1
 */
@Getter
@Slf4j
class State {
    Context originalContext;
    Context context;
    AtomicInteger count = new AtomicInteger(0);
    ;

    void release() {

        if (context != null) {
            try {
                context.release();
            } catch (Throwable t) {
                log.error("While releasing {}: {}", context, t.getMessage(), t);
            }
        }
    }

    void begin() {

        if (count.get() == 0) {
            originalContext = MgnlContext.hasInstance() ? MgnlContext.getInstance() : null;

        }
        count.incrementAndGet();
    }
    void end(boolean releaseAfterExecution) {
        int newCount = count.decrementAndGet();
        if (newCount == 0) {
            if (releaseAfterExecution) {
                release();
            }
        }
    }
}
