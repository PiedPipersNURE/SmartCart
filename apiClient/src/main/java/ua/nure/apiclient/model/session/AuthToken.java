package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is an authentication token used to authenticate the user in SmartCart.API
 */
public class AuthToken {

    /**
     * The JWT token.
     */
    private final String token;

    /**
     * This is the constructor of the AuthToken class.
     * @param token The token.
     */
    public AuthToken(String token) {
        checkNotNull(token, "The token cannot be null.");
        this.token = token;
    }

    /**
     * This method is used to get the token.
     * @return The token.
     */
    public String token() {
        return token;
    }

    /**
     * This method is used to check if the token is empty.
     * @return True if the token is empty, false otherwise.
     */
    public boolean IsEmpty() {
        return token.isEmpty();
    }

    /**
     * This method is used to check if the token is not empty.
     * @return True if the token is not empty, false otherwise.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
