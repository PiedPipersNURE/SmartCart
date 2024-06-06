package ua.nure.apiclient.parser;

import com.google.common.base.Preconditions;

import java.util.List;

import ua.nure.apiclient.model.core.Cart;

public class CartParser {
    public List<Cart> parseCarts(String json) {
        Preconditions.checkNotNull(json, "The JSON cannot be null.");
        Preconditions.checkArgument(!json.isEmpty(), "The JSON cannot be empty.");

        return null;
    }
}
