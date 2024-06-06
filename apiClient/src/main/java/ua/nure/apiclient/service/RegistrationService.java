package ua.nure.apiclient.service;

import ua.nure.apiclient.request.JWTRequestThread;

/**
 * This is a service class that is used to register the user.
 */
public class RegistrationService {

    /**
     * This method is used to register the user.
     */
    public void register(String email, String username, String password, String fullName) {
        // Construct the request body in JSON format
        String requestBody = String.format("{\"email\": \"%s\", \"username\": \"%s\", \"password\": \"%s\", \"fullName\": \"%s\"}",
                email, username, password, fullName);

        String url = "https://172.22.22.69:5160/account/registration";
        JWTRequestThread requestThread = new JWTRequestThread(url, requestBody);
        requestThread.start();
    }
}
