package ua.nure.apiclient.service;

import ua.nure.apiclient.model.GoogleAccountDetails;

/**
 * This class is used to authenticate the user.
 */
public class AuthenticationService {
    private final GoogleAccountDetails account;
    public AuthenticationService(GoogleAccountDetails account) {
        this.account = account;
    }

    /**
     * This method is used to authenticate the user.
     *
     * @return The token.
     */
    public String authenticate() {
        // here we would authenticate the user and return the JWT token
        return null;
    }

    public GoogleAccountDetails account() {
        return account;
    }
}
