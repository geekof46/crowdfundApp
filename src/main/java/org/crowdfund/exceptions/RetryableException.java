package org.crowdfund.exceptions;

/**
 * class to throw retryable exception
 */
public class RetryableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Retryable exception.
     *
     * @param msg the msg
     */
    public RetryableException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Retryable exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public RetryableException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Retryable exception.
     *
     * @param nested the nested
     */
    public RetryableException(final Throwable nested) {
        super(nested);
    }
}
