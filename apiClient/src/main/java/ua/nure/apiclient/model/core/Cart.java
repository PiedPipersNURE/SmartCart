package ua.nure.apiclient.model.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;


/**
 * This is a container of user goods. It is used to store goods that user wants to buy and
 * that user has already bought.
 */
public class Cart {

    /**
     * The unique identifier of the cart.
     */
    private final String cartID;

    /**
     * The name of the cart.
     */
    private final String cartName;

    /**
     * The unique identifier of the owner of the cart.
     */
    private final String cartOwnerID;

    public Cart(String cartName, String cartOwnerID) {

        checkNotNull(cartName,  "Cart name cannot be null");

        checkArgument(!cartName.isEmpty(), "Cart name cannot be empty");

        this.cartName = cartName;
        this.cartID = UUID.randomUUID().toString();
        this.cartOwnerID = cartOwnerID;
    }

    public Cart(String cartID, String cartName, String cartOwnerID) {

        checkNotNull(cartID, "Cart id cannot be null");
        checkNotNull(cartName,  "Cart name cannot be null");
        checkNotNull(cartOwnerID, "Owner id cannot be null");

        checkArgument(!cartID.isEmpty(), "Cart id cannot be empty");
        checkArgument(!cartName.isEmpty(), "Cart name cannot be empty");
        checkArgument(!cartOwnerID.isEmpty(), "Owner id cannot be empty");

        this.cartID = cartID;
        this.cartName = cartName;
        this.cartOwnerID = cartOwnerID;
    }

    /**
     * Returns the name of the cart.
     *
     * @return the name of the cart
     */
    public String cartName() {
        return cartName;
    }

    /**
     * Returns the unique identifier of the cart.
     *
     * @return the unique identifier of the cart
     */
    public String cartId() {
        return cartID;
    }

    /**
     * Returns the unique identifier of the owner of the cart.
     *
     * @return the unique identifier of the owner of the cart
     */
    public String ownerId() {
        return cartOwnerID;
    }
}
