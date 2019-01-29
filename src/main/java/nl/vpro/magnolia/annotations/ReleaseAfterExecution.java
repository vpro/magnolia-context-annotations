package nl.vpro.magnolia.annotations;

/**
 * @author Michiel Meeuwissen
 * @since 1.2
 */
public enum ReleaseAfterExecution {
    /**
     * The system context will be released in any case.
     */
    TRUE,
    /**
     * The system context will be released in no case. Since you cannot always know if the system context will be used in the same thread again, this may be the best value.
     */
    FALSE,
     /**
     * The system context will be released if there is no current system context to start with.
     */
    SMART;
}
