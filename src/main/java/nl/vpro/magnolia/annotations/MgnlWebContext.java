package nl.vpro.magnolia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.common.annotations.Beta;

/**
 *  There is much code that actually required a _web context_.
 *
 *  See e.g. https://jira.magnolia-cms.com/browse/MGNLIMG-176
 *
 *  This is experimental
 *
 * @author Michiel Meeuwissen
 * @since 1.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Beta
public @interface MgnlWebContext {


    /**
     * Whether the system context must be release after execution.
     *
     * A system context cannot really be released, but if this is true, the thread locals will be in the same state afterwards.
     */
    ReleaseAfterExecution releaseAfterExecution() default ReleaseAfterExecution.SMART;


    /**
     *
     */
    @Beta
    String site() default "";
}
