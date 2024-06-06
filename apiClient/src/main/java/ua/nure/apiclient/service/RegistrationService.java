package ua.nure.apiclient.service;


import java.util.Map;

import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.request.UniversalRequestThread;

public class RegistrationService {

    /**
     * This method is used to register the user.
     */
    public AuthToken register(String email, String username, String password, String fullName) {
        String requestBody = String.format("{\"Email\": \"%s\", \"Username\": \"%s\", \"Password\": \"%s\", \"Fullname\": \"%s\", \"isGoogleAuth\": false}",
                email, username, password, fullName);

        String url = "http://172.22.22.69:5160/account/registration";
        UniversalRequestThread requestThread = new UniversalRequestThread(url, requestBody, "POST", Map.of(), "application/json");
        requestThread.start();

        try {
            requestThread.join();
            String jwtToken = requestThread.getResponseToken();
            return new AuthToken(jwtToken);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
