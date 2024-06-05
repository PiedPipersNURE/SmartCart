package ua.nure.apiclient.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a good that can be added to the cart. It is used to store information about the good.
 */
public class Product {

    /**
     * The unique identifier of the product.
     */
    private final String productId;

    /**
     * The assigned cart id of the product.
     */
    private final String cartId;

    /**
     * The name of the product.
     */
    private final String productName;

    /**
     * The assigned user id of the product.
     */
    private final String buyerId;


    /**
     * The quantity of the product.
     */
    private int quantity;

    /**
     * The estimated date of the product.
     */
    private final String estimatedDate;

    public Product(String productId, String cartId, String productName, String buyerId, int quantity, String estimatedDate) {

        checkNotNull(productId, "Product id cannot be null");
        checkNotNull(cartId, "Cart id cannot be null");
        checkNotNull(productName, "Product name cannot be null");
        checkNotNull(buyerId, "Buyer id cannot be null");
        checkNotNull(estimatedDate, "Estimated date cannot be null");

        checkArgument(!productId.isEmpty(), "Product id cannot be empty");
        checkArgument(!cartId.isEmpty(), "Cart id cannot be empty");
        checkArgument(!productName.isEmpty(), "Product name cannot be empty");
        checkArgument(!buyerId.isEmpty(), "Buyer id cannot be empty");
        checkArgument(quantity > 0, "Quantity cannot be less than 1");

        this.productId = productId;
        this.cartId = cartId;
        this.productName = productName;
        this.buyerId = buyerId;
        this.quantity = quantity;
        this.estimatedDate = estimatedDate;
    }

    /**
     * Returns the unique identifier of the product.
     *
     * @return the unique identifier of the product
     */
    public String productId() {
        return productId;
    }

    /**
     * Returns the assigned cart id of the product.
     *
     * @return the assigned cart id of the product
     */
    public String cartId() {
        return cartId;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String productName() {
        return productName;
    }

    /**
     * Returns the assigned user id of the product.
     *
     * @return the assigned user id of the product
     */
    public String buyerId() {
        return buyerId;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int quantity() {
        return quantity;
    }

    /**
     * Returns the estimated date of the product.
     *
     * @return the estimated date of the product
     */
    public String estimatedDate() {
        return estimatedDate;
    }
}
