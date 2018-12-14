package nl.vpro.magnolia.annotations;

import info.magnolia.context.MgnlContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with this will implicetely be executed in the Magnolia 'system context'. See {@link MgnlContext#doInSystemContext}
 *
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
    boolean releaseAfterExecution = true;

}
