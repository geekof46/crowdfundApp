package org.crowdfund.exceptions;

/**
 * Class to throw record not found exception when DDB record not exists for user
 */
public class UserNotFoundException extends RetryableException {

    /**
     * Instantiates a new User not found exception.
     *
     * @param msg the msg
     */
    public UserNotFoundException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param msg    the msg
     * @param nested the nested
     */
    public UserNotFoundException(final String msg, final Throwable nested) {
        super(msg, nested);
    }

    /**
     * Instantiates a new User not found exception.
     *
     * @param nested the nested
     */
    public UserNotFoundException(final Throwable nested) {
        super(nested);
    }
}