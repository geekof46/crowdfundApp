package org.crowdfund.exceptions;

import java.io.Serial;

/**
 * class to throw exception when there is failure in service execution
 */
public class InternalServiceException extends NonRetryableException{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Internal service exception.
     *
     * @param msg the msg
     */
    public InternalServiceException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Internal service exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public InternalServiceException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Internal service exception.
     *
     * @param nested the nested
     */
    public InternalServiceException(final Throwable nested) {
        super(nested);
    }
}