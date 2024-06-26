package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a class that stores the details of a Google account.
 */
public class GoogleAccountDetails {

    private final String email;
    private final String username;
    private final String id;

    public GoogleAccountDetails(String email, String username, String id) {

        checkNotNull(email, "The email cannot be null.");
        checkNotNull(username, "The username cannot be null.");
        checkNotNull(id, "The id cannot be null.");

        this.email = email;
        this.username = username;
        this.id = id;
    }

    /**
     * This method is used to get the email.
     * @return The email.
     */
    public String email() {
        return email;
    }

    /**
     * This method is used to get the username.
     * @return The username.
     */
    public String username() {
        return username;
    }

    /**
     * This method is used to get the id.
     * @return The id.
     */
    public String id() {
        return id;
    }
}
