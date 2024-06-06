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

    private ClientSession() {
    }

    public static void startSessionWithGoogle(GoogleAccountDetails credentials) {
        checkNotNull(credentials, "The credentials cannot be null.");
        if (!IsInSession) {
            smartCartClient = new SmartCartClient(credentials);
            IsInSession = true;
        }
    }

    public static void startSession(LoginDetails token) {
        checkNotNull(token, "The token cannot be null.");
        if (!IsInSession) {
            smartCartClient = new SmartCartClient(token);
            IsInSession = true;
        }
    }

    public static void endSession() {
        if (IsInSession) {
            smartCartClient = null;
            IsInSession = false;
        }
    }

    public static SmartCartClient getSmartCartClient() {
        if (IsInSession) {
            return smartCartClient;
        }
        throw new IllegalStateException("The session is not started.");
    }

    public static boolean isInSession() {
        return IsInSession && !smartCartClient.token().IsEmpty();
    }
}
