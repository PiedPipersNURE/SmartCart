package ua.nure.apiclient.service;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.apiclient.model.core.Cart;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.parser.CartParser;
import ua.nure.apiclient.request.GetRequestThread;

/**
 * This is a service that is used to manage the cart.
 */
public class CartService {

    private final String suffix = "/api/Cart";
    private final String baseUrl;
    private final AuthToken authToken;
    private final CartParser cartParser = new CartParser();

    /**
     * This is the constructor of the service.
     * @param token The authentication token.
     * @param baseUrl The base URL.
     */
    public CartService(AuthToken token, String baseUrl) {
        Preconditions.checkNotNull(token, "The token cannot be null.");
        Preconditions.checkNotNull(baseUrl, "The base URL cannot be null.");
        this.authToken = token;
        this.baseUrl = baseUrl;
    }

    /**
     * This method is used to get all the carts.
     * @return The list of {@link Cart}.
     */
    public List<Cart> getAllCarts() throws InterruptedException {
        String url = baseUrl + suffix + "/getAll";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        return cartParser.parseCarts(response);
    }

    public AuthToken authToken() {
        return authToken;
    }
}
