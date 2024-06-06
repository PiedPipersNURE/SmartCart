package ua.nure.apiclient;

import static com.google.common.base.Preconditions.checkNotNull;

import ua.nure.apiclient.model.session.GoogleAccountDetails;
import ua.nure.apiclient.model.session.LoginDetails;

/**
 * This is a singleton class that is used to manage the client session.
 */
public class ClientSession {

    private static boolean IsInSession = false;

    private static SmartCartClient smartCartClient;

    private static String userEmail;

    private ClientSession() {
    }

    /**
     * This method is used to start a session with Google.
     * @param credentials The credentials.
     */
    public static void startSessionWithGoogle(GoogleAccountDetails credentials) {
        checkNotNull(credentials, "The credentials cannot be null.");
        if (!IsInSession) {
            try {
                smartCartClient = new SmartCartClient(credentials);
                IsInSession = true;
                userEmail = credentials.email();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid credentials.");
            }
        }
    }

    /**
     * This method is used to start a session with the given token.
     * @param credentials The token.
     */
    public static void startSession(LoginDetails credentials) {
        checkNotNull(credentials, "The credentials cannot be null.");
        if (!IsInSession) {
            try {
                smartCartClient = new SmartCartClient(credentials);
                IsInSession = true;
                userEmail = credentials.email();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid credentials.");
            }
        }
    }

    /**
     * This method is used to end the session.
     */
    public static void endSession() {
        if (IsInSession) {
            smartCartClient = null;
            IsInSession = false;
            userEmail = "";
        }
    }

    /**
     * This method is used to get the SmartCart client.
     * @return The SmartCart client.
     */
    public static SmartCartClient getSmartCartClient() {
        if (IsInSession) {
            return smartCartClient;
        }
        throw new IllegalStateException("The session is not started.");
    }

    /**
     * This method is used to check if the session is started.
     * @return True if the session is started, false otherwise.
     */
    public static boolean isInSession() {
        return IsInSession && !smartCartClient.token().IsEmpty();
    }

    /**
     * This method is used to get the email of the user.
     * @return The email of the user.
     */
    public static String getUserEmail() {
        return userEmail;
    }
}
