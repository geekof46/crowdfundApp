package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists for Project
 */
public class ProjectNotFoundException extends RetryableException {

    /**
     * Instantiates a new Project not found exception.
     *
     * @param msg the msg
     */
    public ProjectNotFoundException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Project not found exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public ProjectNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Project not found exception.
     *
     * @param nested the nested
     */
    public ProjectNotFoundException(final Throwable nested) {
        super(nested);
    }
}