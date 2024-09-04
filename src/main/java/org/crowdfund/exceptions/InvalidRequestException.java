package org.crowdfund.exceptions;

import java.io.Serial;

/**
 * class to throw exception for invalid input
 */
public class InvalidRequestException extends NonRetryableException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Invalid request exception.
     *
     * @param msg the msg
     */
    public InvalidRequestException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Invalid request exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public InvalidRequestException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Invalid request exception.
     *
     * @param nested the nested
     */
    public InvalidRequestException(final Throwable nested) {
        super(nested);
    }
}
