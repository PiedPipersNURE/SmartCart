package ua.nure.apiclient.model.session;

public class AuthToken {

    private final String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public String token() {
        return token;
    }

    public boolean IsEmpty() {
        return token.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
