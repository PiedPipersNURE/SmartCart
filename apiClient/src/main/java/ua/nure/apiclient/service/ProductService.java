package ua.nure.apiclient.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ua.nure.apiclient.model.core.Product;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.parser.ProductParser;
import ua.nure.apiclient.request.GetRequestThread;
import ua.nure.apiclient.request.PostPutRequestThread;

/**
 * This is a service that is used to manage the products.
 */
public class ProductService {

    private static final String suffix = "/api/Product";
    private final String baseUrl;
    private final String authToken;
    private final ProductParser productParser = new ProductParser();

    /**
     * This is the constructor of the service.
     *
     * @param authToken The authentication token.
     * @param baseUrl   The base URL.
     */
    public ProductService(AuthToken authToken, String baseUrl) {
        checkNotNull(authToken, "The token cannot be null.");
        checkNotNull(baseUrl, "The base URL cannot be null.");
        this.baseUrl = baseUrl;
        this.authToken = authToken.token();
    }

    /**
     * This method is used to get all the products.
     *
     * @return The list of products.
     * @throws InterruptedException If the thread is interrupted.
     */
    public List<Product> getProducts() throws InterruptedException {
        String url = baseUrl + suffix + "/getAll";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        return productParser.parseProducts(response);
    }

    /**
     * This method is used to get a product by its buyer id.
     *
     * @param id The id user assigned to buy this product.
     * @return The product.
     * @throws InterruptedException If the thread is interrupted.
     */
    public List<Product> getProductByBuyerId(String id) throws InterruptedException {
        String url = baseUrl + suffix + "/getByUserId/" + id;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        return productParser.parseProducts(response);
    }

    /**
     * This method is used to get a product by its id and cart id.
     *
     * @param userid The id of the user.
     * @param cartId The id of the cart.
     * @return The product.
     * @throws InterruptedException If the thread is interrupted.
     */
    public List<Product> getProductByIdAndCartId(String userid, String cartId) throws InterruptedException {
        String url = baseUrl + suffix + "/getByCartAndUser/" + cartId + "/" + userid;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        return productParser.parseProducts(response);
    }

    /**
     * This method is used to get a product by its id.
     *
     * @param cartId The id of the cart
     * @param productName The name of the product.
     * @param quantity The quantity of the product.
     *
     * @return The product.
     * @throws InterruptedException If the thread is interrupted.
     */
    public Optional<Product> addProductToCart(String cartId, String productName, int quantity, String buyerId) throws InterruptedException {

        String url = baseUrl + suffix + "/add";

        var product = new Product(cartId, productName, quantity, buyerId);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        String requestBody = String.format("{\"productID\": \"%s\", \"cartID\": \"%s\", " +
                        "\"productName\": \"%s\", \"productQuantity\": \"%s\", \"buyerID\": \"%s\"}",
                product.productId(), product.cartId(), product.productName(), product.quantity(), product.buyerId());

        PostPutRequestThread requestThread = new PostPutRequestThread(url, requestBody,
                "POST", headers, "application/json");

        requestThread.start();
        requestThread.join();

        String response = requestThread.getResponseToken();

        if (!response.equals("true")) {
            return Optional.empty();
        }

        return Optional.of(product);
    }


    public boolean changeProductStatus(Product product) throws InterruptedException {

        String url = baseUrl + suffix + "/update";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        String requestBody = String.format("{\"productID\": \"%s\", \"cartID\": \"%s\", " +
                "\"productName\": \"%s\", \"productQuantity\": %s, \"buyerID\": \"%s\", \"isBought\": %s}",
                product.productId(), product.cartId(), product.productName(), product.quantity(), product.buyerId(), !product.isBought());

        PostPutRequestThread requestThread = new PostPutRequestThread(url, requestBody, "PUT", headers, "application/json");
        requestThread.start();
        requestThread.join();

        String response = requestThread.getResponseToken();

        return response.equals("true");
    }
}
