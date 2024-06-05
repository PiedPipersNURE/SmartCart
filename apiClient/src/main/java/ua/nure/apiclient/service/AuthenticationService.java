package ua.nure.apiclient.service;

import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.model.session.GoogleAccountDetails;
import ua.nure.apiclient.model.session.LoginDetails;

/**
 * This class is used to authenticate the user.
 */
public class AuthenticationService {

    /**
     * This method is used to authenticate the user.
     *
     * @return The token.
     */
    public AuthToken authenticate(LoginDetails loginDetails) {
        // here we would authenticate the user and return the JWT token
        return null;
    }

    /**
     * This method is used to authenticate the user.
     *
     * @return The token.
     */
    public AuthToken authenticate(GoogleAccountDetails googleAccountDetails) {
        // here we would authenticate the user and return the JWT token
        return null;
    }
}
