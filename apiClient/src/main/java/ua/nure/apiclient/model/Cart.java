package ua.nure.apiclient.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * This is a container of user goods. It is used to store goods that user wants to buy and
 * that user has already bought.
 */
public class Cart {

    /**
     * The unique identifier of the cart.
     */
    private final String cartId;

    /**
     * The name of the cart.
     */
    private final String cartName;

    /**
     * The unique identifier of the owner of the cart.
     */
    private final String ownerId;

    public Cart(String cartId, String cartName, String ownerId) {

        checkNotNull(cartId, "Cart id cannot be null");
        checkNotNull(cartName,  "Cart name cannot be null");
        checkNotNull(ownerId, "Owner id cannot be null");

        checkArgument(!cartId.isEmpty(), "Cart id cannot be empty");
        checkArgument(!cartName.isEmpty(), "Cart name cannot be empty");
        checkArgument(!ownerId.isEmpty(), "Owner id cannot be empty");

        this.cartId = cartId;
        this.cartName = cartName;
        this.ownerId = ownerId;
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
        return cartId;
    }

    /**
     * Returns the unique identifier of the owner of the cart.
     *
     * @return the unique identifier of the owner of the cart
     */
    public String ownerId() {
        return ownerId;
    }
}
