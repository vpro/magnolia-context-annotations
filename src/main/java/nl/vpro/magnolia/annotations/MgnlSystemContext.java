package nl.vpro.magnolia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MgnlSystemContext {


    /**
     * Whether the system context must be release after execution.
     *
     * A system context cannot really be released, but if this is true, the thread locals will be in the same state afterwards.
     */
    ReleaseAfterExecution releaseAfterExecution() default ReleaseAfterExecution.SMART;

}
