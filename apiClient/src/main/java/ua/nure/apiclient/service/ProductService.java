package ua.nure.apiclient.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.apiclient.model.core.Product;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.request.GetRequestThread;

public class ProductService {
    private final String baseUrl = "http://172.22.22.69:5158/api/Cart/";
    private final String authToken;

    public ProductService(AuthToken authToken) {
        this.authToken = authToken.token();
    }

    public List<Product> getProducts() {
        String url = baseUrl + "getAll";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        try {
            getRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String response = getRequest.getResponse();

        List<Product> products = new ArrayList<>();

        return products;
    }
}
