package nl.vpro.magnolia.annotations;

import info.magnolia.context.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michiel Meeuwissen
 * @since 1.1
 */
@Getter
@Slf4j
class State<T extends Context> {
    Context originalContext;
    T context;
    final AtomicInteger count = new AtomicInteger(0);

    void release() {
        if (context != null) {
            try {
                 if (context instanceof SystemContext) {
                     if (context instanceof ThreadDependentSystemContext) {
                         ((ThreadDependentSystemContext) context).releaseThread();
                     }
                 } else {
                     context.release();
                 }
            } catch (Throwable t) {
                log.error("While releasing {}: {}", context, t.getMessage(), t);
            }
        }
    }

    void smartRelease() {
         if (context != null) {
            try {
                if (originalContext instanceof WebContext) {
                    log.debug("Not releasing the context, since the original context is a web context, and we don't know if the thread is used any further");
                } else {
                    if (!Objects.equals(context, originalContext)) {
                        release();
                    } else {
                        log.debug("Not releasing context, while that would release the original context");
                    }
                }
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

    void end(ReleaseAfterExecution releaseAfterExecution) {
        int newCount = count.decrementAndGet();
        if (newCount == 0) {
            switch(releaseAfterExecution) {
                case FALSE:
                    break;
                case TRUE:
                    release();
                    break;
                case SMART:
                    smartRelease();
            }
        }
    }
}
