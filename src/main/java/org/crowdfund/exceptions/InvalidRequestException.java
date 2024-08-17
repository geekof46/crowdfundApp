package org.crowdfund.exceptions;

import java.io.Serial;

/**
 * class to throw exception for invalid input
 */
public class InvalidRequestException extends NonRetryableException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidRequestException(final String msg) {
        super(msg);
    }

    public InvalidRequestException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public InvalidRequestException(final Throwable nested) {
        super(nested);
    }
}
