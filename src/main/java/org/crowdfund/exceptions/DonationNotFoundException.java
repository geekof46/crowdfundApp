package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists
 */
public class DonationNotFoundException extends RetryableException {

    public DonationNotFoundException(final String msg) {
        super(msg);
    }

    public DonationNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public DonationNotFoundException(final Throwable nested) {
        super(nested);
    }
}