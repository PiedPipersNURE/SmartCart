package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

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

    public String email() {
        return email;
    }

    public String username() {
        return username;
    }

    public String id() {
        return id;
    }
}
