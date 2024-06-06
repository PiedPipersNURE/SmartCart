package ua.nure.apiclient.service;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.apiclient.model.core.Cart;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.parser.CartParser;
import ua.nure.apiclient.request.DeleteRequestThread;
import ua.nure.apiclient.request.GetRequestThread;
import ua.nure.apiclient.request.PostPutRequestThread;

/**
 * This is a service that is used to manage the cart.
 */
public class CartService {

    private final String suffix = "/api/Cart";
    private final String baseUrl;
    private final String authToken;
    private final CartParser cartParser = new CartParser();

    /**
     * This is the constructor of the service.
     *
     * @param token   The authentication token.
     * @param baseUrl The base URL.
     */
    public CartService(AuthToken token, String baseUrl) {
        Preconditions.checkNotNull(token, "The token cannot be null.");
        Preconditions.checkNotNull(baseUrl, "The base URL cannot be null.");
        this.authToken = token.token();
        this.baseUrl = baseUrl;
    }

    /**
     * This method is used to get all the carts.
     *
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

    public String authToken() {
        return authToken;
    }


    /**
     * This method is used to add a cart.
     *
     * @param cart The cart to add.
     */
    public void addCart(Cart cart) {
        String url = baseUrl + suffix + "/post";

        String requestBody = String.format("{\"cartID\": \"%s\", \"cartName\": \"%s\", " +
                        "\"cartOwnerID\": \"%s\"}",
                cart.cartId(), cart.cartName(), cart.ownerId());

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + authToken);

        PostPutRequestThread requestThread = new PostPutRequestThread(url, requestBody,
                "POST", headers, "application/json");
        requestThread.start();

        try {
            requestThread.join();
            String result = requestThread.getResponseToken();
            if (result.equals("false")) {
                throw new IllegalArgumentException("The cart already exists.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to delete a cart.
     *
     * @param cart The cart to delete.
     */
    public void deleteCart(Cart cart) {
        String url = baseUrl + suffix + "/delete/" + cart.cartId();

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + authToken);

        DeleteRequestThread requestThread = new DeleteRequestThread(url, headers);
        requestThread.start();

        try {
            requestThread.join();
            String result = requestThread.getResponse();
            if (!result.equals("true")) {
                throw new IllegalArgumentException("The cart does not exist.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
