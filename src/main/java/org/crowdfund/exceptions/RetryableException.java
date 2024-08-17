package org.crowdfund.exceptions;

/**
 * class to throw retryable exception
 */
public class RetryableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RetryableException(final String msg) {
        super(msg);
    }

    public RetryableException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public RetryableException(final Throwable nested) {
        super(nested);
    }
}
