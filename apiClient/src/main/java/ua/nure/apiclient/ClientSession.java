package ua.nure.apiclient;

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

    private ClientSession(Credentials credentials) {
        client = new SmartCartClient(credentials);
    }

    /**
     * This method is used to get the instance of the ClientSession class.
     *
     * @return The instance of the ClientSession class.
     */
    public static ClientSession getInstance(Credentials credentials) {
        if (instance == null) {
            instance = new ClientSession(credentials);
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
