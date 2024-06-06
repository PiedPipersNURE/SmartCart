package ua.nure.apiclient.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import ua.nure.apiclient.model.core.CartMember;
import ua.nure.apiclient.model.session.AuthToken;

/**
 * This is a service that is used to manage the cart members.
 */
public class MembersService {

    private final String suffix = "/api/CartMember";
    private final String baseUrl;
    private final String authToken;

    public MembersService(AuthToken token, String baseUrl) {
        checkNotNull(token, "The token cannot be null.");
        checkNotNull(baseUrl, "The base URL cannot be null.");
        this.baseUrl = baseUrl;
        this.authToken = token.token();
    }

    public List<CartMember> getMembers() {
        return null;
    }
}
