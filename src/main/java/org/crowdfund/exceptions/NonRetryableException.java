package org.crowdfund.exceptions;

/**
 * class to non retryable exception
 */
public class NonRetryableException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Non retryable exception.
     *
     * @param msg the msg
     */
    public NonRetryableException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Non retryable exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public NonRetryableException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Non retryable exception.
     *
     * @param nested the nested
     */
    public NonRetryableException(final Throwable nested) {
        super(nested);
    }
}
