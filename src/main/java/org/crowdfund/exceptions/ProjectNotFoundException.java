package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists for Project
 */
public class ProjectNotFoundException extends RetryableException {

    public ProjectNotFoundException(final String msg) {
        super(msg);
    }

    public ProjectNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public ProjectNotFoundException(final Throwable nested) {
        super(nested);
    }
}