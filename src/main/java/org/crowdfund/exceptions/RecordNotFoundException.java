package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists
 */
public class RecordNotFoundException extends RetryableException {

    /**
     * Instantiates a new Record not found exception.
     *
     * @param msg the msg
     */
    public RecordNotFoundException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Record not found exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public RecordNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Record not found exception.
     *
     * @param nested the nested
     */
    public RecordNotFoundException(final Throwable nested) {
        super(nested);
    }
}