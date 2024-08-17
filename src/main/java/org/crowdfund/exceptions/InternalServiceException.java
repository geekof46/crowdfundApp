package org.crowdfund.exceptions;

import java.io.Serial;

/**
 * class to throw exception when there is failure in service execution
 */
public class InternalServiceException extends NonRetryableException{
    @Serial
    private static final long serialVersionUID = 1L;

    public InternalServiceException(final String msg) {
        super(msg);
    }

    public InternalServiceException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public InternalServiceException(final Throwable nested) {
        super(nested);
    }
}