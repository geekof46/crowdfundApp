package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists for user
 */
public class UserNotFoundException extends RetryableException {

    public UserNotFoundException(final String msg) {
        super(msg);
    }

    public UserNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    public UserNotFoundException(final Throwable nested) {
        super(nested);
    }
}