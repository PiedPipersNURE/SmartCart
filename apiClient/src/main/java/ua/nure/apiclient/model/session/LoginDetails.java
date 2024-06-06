package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a class that stores the details of a login.
 */
public class LoginDetails {

    private final String email;
    private final String password;

    /**
     * This is the constructor of the LoginDetails class.
     * @param email The email.
     * @param password The password.
     */
    public LoginDetails(String email, String password) {
        checkNotNull(email, "The email cannot be null.");
        checkNotNull(password, "The password cannot be null.");
        this.email = email;
        this.password = password;
    }

    /**
     * This method is used to get the email.
     * @return The email.
     */
    public String email() {
        return email;
    }

    /**
     * This method is used to get the password.
     * @return The password.
     */
    public String password() {
        return password;
    }
}
