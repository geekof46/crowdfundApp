package org.crowdfund.exceptions;

import software.amazon.awssdk.core.exception.RetryableException;

/**
 * class to throw retryable dependency exception to reprocess the request
 */
public class RetryableDependencyException extends RetryableException {

    private static final long serialVersionUID = 1L;

    public RetryableDependencyException(final String msg) {
        super(msg);
    }

    public RetryableDependencyException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public RetryableDependencyException(final Throwable nested) {
        super(nested);
    }
}