package ua.nure.apiclient;

public class Credentials {

    private final String googleKey;

    public Credentials(String googleKey) {
        this.googleKey = googleKey;
    }

    public String getGoogleKey() {
        return googleKey;
    }
}
