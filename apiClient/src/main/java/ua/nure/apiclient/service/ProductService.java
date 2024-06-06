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

    private final String suffix = "/api/Product";
    private final String baseUrl;
    private final String authToken;
    private final ProductParser productParser = new ProductParser();

    public ProductService(AuthToken authToken, String baseUrl) {
        checkNotNull(authToken, "The token cannot be null.");
        checkNotNull(baseUrl, "The base URL cannot be null.");
        this.baseUrl = baseUrl;
        this.authToken = authToken.token();
    }

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
}
