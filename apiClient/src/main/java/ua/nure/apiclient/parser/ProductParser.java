package ua.nure.apiclient.parser;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ua.nure.apiclient.model.core.Product;

/**
 * This is a parser that is used to parse the products.
 */
public class ProductParser {

    /**
     * This method is used to parse the products.
     * @param json The JSON.
     * @return The list of products.
     */
    public List<Product> parseProducts(String json) {
        checkNotNull(json, "The JSON cannot be null.");
        checkArgument(!json.isEmpty(), "The JSON cannot be empty.");

        Gson gson = new Gson();
        Type productListType = new TypeToken<List<Product>>(){}.getType();
        return gson.fromJson(json, productListType);
    }
}
