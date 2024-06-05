package ua.nure.apiclient;

import static com.google.common.base.Preconditions.checkNotNull;

import ua.nure.apiclient.model.GoogleAccountDetails;

/**
 * This is a singleton class that is used to manage the client session.
 */
public class ClientSession {

    /**
     * This is an instance of the ClientSession class.
     */
    private static ClientSession instance;

    /**
     * This is a client that is used to interact with the SmartCart API.
     */
    private final SmartCartClient client;

    private ClientSession(GoogleAccountDetails account) {
        checkNotNull(account, "The account cannot be null.");
        client = new SmartCartClient(account);
    }

    /**
     * This method is used to get the instance of the ClientSession class.
     *
     * @return The instance of the ClientSession class.
     */
    public static ClientSession getInstance(GoogleAccountDetails account) {
        checkNotNull(account, "The account cannot be null.");
        if (instance == null) {
            instance = new ClientSession(account);
        }
        return instance;
    }

    /**
     * This method is used to get the client.
     *
     * @return The client.
     */
    public SmartCartClient getClient() {
        return client;
    }
}
