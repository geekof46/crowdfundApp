package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists
 */
public class RecordNotFoundException extends RetryableException {

    public RecordNotFoundException(final String msg) {
        super(msg);
    }

    public RecordNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public RecordNotFoundException(final Throwable nested) {
        super(nested);
    }
}