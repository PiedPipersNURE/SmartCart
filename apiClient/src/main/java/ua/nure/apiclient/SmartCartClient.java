package ua.nure.apiclient;

import ua.nure.apiclient.model.GoogleAccountDetails;
import ua.nure.apiclient.service.AuthenticationService;
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
    private final String token;

    public SmartCartClient(GoogleAccountDetails credentials) {
        this.cartService = new CartService();
        this.membersService = new MembersService();
        this.productService = new ProductService();
        this.token = new AuthenticationService(credentials).authenticate();
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
    public String token() {
        return token;
    }
}