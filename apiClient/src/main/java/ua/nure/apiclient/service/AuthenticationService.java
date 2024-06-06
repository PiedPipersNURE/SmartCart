package ua.nure.apiclient.service;

import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.model.session.GoogleAccountDetails;
import ua.nure.apiclient.model.session.LoginDetails;
import ua.nure.apiclient.request.GetRequestThread;

/**
 * This class is used to authenticate the user.
 */
public class AuthenticationService {

    public AuthToken authenticate(LoginDetails loginDetails) {
        String email = loginDetails.email();
        String password = loginDetails.password();
        String url = "http://172.22.22.69:5160/account/login?email=" + email + "&password=" + password;

        GetRequestThread getRequest = new GetRequestThread(url);
        getRequest.start();

        try {
            getRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String response = getRequest.getResponse();

        if (response != null) {
            return new AuthToken(response);
        } else {
            return null;
        }
    }

    /**
     * This method is used to authenticate the user.
     *
     * @return The token.
     */
    public AuthToken authenticate(GoogleAccountDetails googleAccountDetails) {
        String url = "http://172.22.22.69:5160/account/google-mobile-login?googleID=" + googleAccountDetails.id() + "&email=" + googleAccountDetails.email() + "&username=" + googleAccountDetails.username();

        GetRequestThread getRequest = new GetRequestThread(url);
        getRequest.start();

        try {
            getRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String response = getRequest.getResponse();

        if (response != null) {
            return new AuthToken(response);
        } else {
            return null;
        }
    }
}
