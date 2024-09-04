package org.crowdfund.utils;

import lombok.NonNull;

/**
 * The type Validator util.
 */
public final class ValidatorUtil {

    private ValidatorUtil(){}

    /**
     * Is valid email id boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isValidEmailId(@NonNull final String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
