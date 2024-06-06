package ua.nure.apiclient.service;

import com.google.common.base.Preconditions;

import java.util.List;

import ua.nure.apiclient.model.core.Cart;
import ua.nure.apiclient.model.session.AuthToken;

/**
 * This is a service that is used to manage the cart.
 */
public class CartService {

    private final String suffix = "/api/Cart";
    private final String baseUrl;
    private final AuthToken authToken;

    public CartService(AuthToken token, String baseUrl) {
        Preconditions.checkNotNull(token, "The token cannot be null.");
        Preconditions.checkNotNull(baseUrl, "The base URL cannot be null.");
        this.authToken = token;
        this.baseUrl = baseUrl;
    }

    public List<Cart> getAllCarts() {
        return null;
    }

    public AuthToken authToken() {
        return authToken;
    }
}
