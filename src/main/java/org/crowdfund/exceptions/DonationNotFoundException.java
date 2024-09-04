package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists
 */
public class DonationNotFoundException extends RetryableException {

    /**
     * Instantiates a new Donation not found exception.
     *
     * @param msg the msg
     */
    public DonationNotFoundException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Donation not found exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public DonationNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new Donation not found exception.
     *
     * @param nested the nested
     */
    public DonationNotFoundException(final Throwable nested) {
        super(nested);
    }
}