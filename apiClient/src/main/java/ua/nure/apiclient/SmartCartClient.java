package ua.nure.apiclient;

import static com.google.common.base.Preconditions.checkNotNull;

import ua.nure.apiclient.service.CartService;
import ua.nure.apiclient.service.MembersService;
import ua.nure.apiclient.service.ProductService;

/**
 * This is a singleton class that is used to interact with the SmartCart API.
 */
public class SmartCartClient {

    private final CartService cartService;
    private final MembersService membersService;
    private final ProductService productService;
    private final Credentials credentials;

    public SmartCartClient(Credentials credentials) {
        this.cartService = new CartService();
        this.membersService = new MembersService();
        this.productService = new ProductService();
        this.credentials = checkNotNull(credentials);
    }

    /**
     * This method is used to get the cart service.
     * @return The cart service.
     */
    public CartService cartService() {
        return cartService;
    }

    /**
     * This method is used to get the members service.
     * @return The members service.
     */
    public MembersService membersService() {
        return membersService;
    }

    /**
     * This method is used to get the product service.
     * @return The product service.
     */
    public ProductService productService() {
        return productService;
    }

    /**
     * This method is used to get the credentials.
     * @return The credentials.
     */
    public Credentials credentials() {
        return credentials;
    }
}