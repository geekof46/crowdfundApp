package org.crowdfund.exceptions;

/**
 * class to non retryable exception
 */
public class NonRetryableException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NonRetryableException(final String msg) {
        super(msg);
    }

    public NonRetryableException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public NonRetryableException(final Throwable nested) {
        super(nested);
    }
}
