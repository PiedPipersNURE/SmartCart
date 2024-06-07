package ua.nure.apiclient;

import ua.nure.apiclient.model.session.GoogleAccountDetails;
import ua.nure.apiclient.model.session.LoginDetails;
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

    /**
     * This constructor is used to create a SmartCart client with the given credentials.
     *
     * @param credentials The credentials.
     */
    public SmartCartClient(GoogleAccountDetails credentials) {
        var tokenResult = new AuthenticationService().authenticate(credentials);
        var baseUrl = "http://172.22.22.69:5158";
        if (tokenResult.isPresent()) {
            this.cartService = new CartService(tokenResult.get(), baseUrl);
            this.membersService = new MembersService(tokenResult.get(), baseUrl);
            this.productService = new ProductService(tokenResult.get(), baseUrl);
        } else {
            throw new IllegalArgumentException("Invalid credentials.");
        }
    }

    /**
     * This constructor is used to create a SmartCart client with the given credentials.
     *
     * @param credentials The credentials.
     */
    public SmartCartClient(LoginDetails credentials) {
        var tokenResult = new AuthenticationService().authenticate(credentials);

        if (tokenResult.isPresent()) {
            this.cartService = new CartService(tokenResult.get(),
                    "http://172.22.22.69:5158");
            this.membersService = new MembersService(tokenResult.get(),
                    "http://172.22.22.69:5158");
            this.productService = new ProductService(tokenResult.get(),
                    "http://172.22.22.69:5158");
        } else {
            throw new IllegalArgumentException("Invalid credentials.");
        }
    }

    /**
     * This method is used to get the cart service.
     *
     * @return The cart service.
     */
    public CartService cartService() {
        return cartService;
    }

    /**
     * This method is used to get the members service.
     *
     * @return The members service.
     */
    public MembersService membersService() {
        return membersService;
    }

    /**
     * This method is used to get the product service.
     *
     * @return The product service.
     */
    public ProductService productService() {
        return productService;
    }

    /**
     * This method is used to get the credentials.
     *
     * @return The credentials.
     */
    public String token() {
        return cartService.authToken();
    }
}