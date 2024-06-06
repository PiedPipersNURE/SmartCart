package ua.nure.apiclient.parser;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ua.nure.apiclient.model.core.CartMember;

public class CartMemberParser {

    /**
     * This method is used to parse the members of cart.
     * @param json The JSON.
     * @return The list of carts.
     */
    public List<CartMember> parseCarts(String json) {
        checkNotNull(json, "The JSON cannot be null.");
        checkArgument(!json.isEmpty(), "The JSON cannot be empty.");

        Gson gson = new Gson();
        Type productListType = new TypeToken<List<CartMember>>(){}.getType();
        return gson.fromJson(json, productListType);
    }
}
