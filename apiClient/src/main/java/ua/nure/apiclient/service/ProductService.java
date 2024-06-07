package ua.nure.apiclient.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.apiclient.model.core.Product;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.parser.ProductParser;
import ua.nure.apiclient.request.GetRequestThread;

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
     * @param authToken The authentication token.
     * @param baseUrl The base URL.
     */
    public ProductService(AuthToken authToken, String baseUrl) {
        checkNotNull(authToken, "The token cannot be null.");
        checkNotNull(baseUrl, "The base URL cannot be null.");
        this.baseUrl = baseUrl;
        this.authToken = authToken.token();
    }

    /**
     * This method is used to get all the products.
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
}
