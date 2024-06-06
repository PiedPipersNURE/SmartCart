package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is an authentication token used to authenticate the user in SmartCart.API
 */
public class AuthToken {

    private final String token;

    public AuthToken(String token) {
        checkNotNull(token, "The token cannot be null.");
        this.token = token;
    }

    public String token() {
        return token;
    }

    public boolean IsEmpty() {
        return token.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
