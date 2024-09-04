package org.crowdfund.exceptions;

/**
 * class to throw retryable dependency exception to reprocess the request
 */
public class RetryableDependencyException extends RetryableException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Retryable dependency exception.
     *
     * @param msg the msg
     */
    public RetryableDependencyException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Retryable dependency exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public RetryableDependencyException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Retryable dependency exception.
     *
     * @param nested the nested
     */
    public RetryableDependencyException(final Throwable nested) {
        super(nested);
    }
}