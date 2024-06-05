package ua.nure.apiclient.model.session;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginDetails {

    private final String email;
    private final String password;

    public LoginDetails(String email, String password) {
        checkNotNull(email, "The email cannot be null.");
        checkNotNull(password, "The password cannot be null.");
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}
