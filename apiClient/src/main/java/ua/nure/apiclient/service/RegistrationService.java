package ua.nure.apiclient.service;

import java.util.Map;
import java.util.Optional;

import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.request.UniversalRequestThread;

/**
 * This is a service that is used to register the user.
 */
public class RegistrationService {

    private static final String url = "http://172.22.22.69:5160/account/registration";;

    /**
     * This method is used to register the user.
     */
    public Optional<AuthToken> register(String email,
                                        String username,
                                        String password,
                                        String fullName) {

        String requestBody = String.format("{\"Email\": \"%s\", \"Username\": \"%s\", " +
                        "\"Password\": \"%s\", \"FullName\": \"%s\", \"isGoogleAuth\": false}",
                            email, username, password, fullName);

        UniversalRequestThread requestThread = new UniversalRequestThread(url, requestBody,
                "POST", Map.of(), "application/json");
        requestThread.start();

        try {
            requestThread.join();
            String jwtToken = requestThread.getResponseToken();
            return Optional.of(new AuthToken(jwtToken));
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }
}
