package ua.nure.apiclient.model.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

/**
 * This is a good that can be added to the cart. It is used to store information about the good.
 */
public class Product {

    /**
     * The unique identifier of the product.
     */
    private final String productID;

    /**
     * The assigned cart id of the product.
     */
    private final String cartID;

    /**
     * The name of the product.
     */
    private final String productName;

    /**
     * The assigned user id of the product.
     */
    private final String buyerID;


    /**
     * The quantity of the product.
     */
    private int productQuantity;

    /**
     * The estimated date of the product.
     */
    private final String endDate;

    private boolean isBought = false;

    public Product(String productID, String cartID, String productName, String buyerID, int productQuantity, String endDate) {

        checkNotNull(productID, "Product id cannot be null");
        checkNotNull(cartID, "Cart id cannot be null");
        checkNotNull(productName, "Product name cannot be null");
        checkNotNull(buyerID, "Buyer id cannot be null");
        checkNotNull(endDate, "Estimated date cannot be null");

        checkArgument(!productID.isEmpty(), "Product id cannot be empty");
        checkArgument(!cartID.isEmpty(), "Cart id cannot be empty");
        checkArgument(!productName.isEmpty(), "Product name cannot be empty");
        checkArgument(!buyerID.isEmpty(), "Buyer id cannot be empty");
        checkArgument(productQuantity > 0, "Quantity cannot be less than 1");

        this.productID = productID;
        this.cartID = cartID;
        this.productName = productName;
        this.buyerID = buyerID;
        this.productQuantity = productQuantity;
        this.endDate = endDate;
    }

    public Product(String cartId, String productName, int quantity, String buyerId) {

        checkNotNull(cartId, "Cart id cannot be null");
        checkNotNull(productName, "Product name cannot be null");
        checkNotNull(buyerId, "Buyer id cannot be null");
        checkArgument(quantity > 0, "Quantity cannot be less than 1");

        this.cartID = cartId;
        this.productName = productName;
        this.productQuantity = quantity;
        this.productID = UUID.randomUUID().toString();
        this.buyerID = buyerId;
        this.endDate = null;
    }

    /**
     * Returns the status of the product.
     * @param bought the status of the product
     */
    public void setBought(boolean bought) {
        isBought = bought;
    }

    /**
     * Returns the unique identifier of the product.
     *
     * @return the unique identifier of the product
     */
    public String productId() {
        return productID;
    }

    /**
     * Returns the assigned cart id of the product.
     *
     * @return the assigned cart id of the product
     */
    public String cartId() {
        return cartID;
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
        return buyerID;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int quantity() {
        return productQuantity;
    }

    /**
     * Returns the estimated date of the product.
     *
     * @return the estimated date of the product
     */
    public String estimatedDate() {
        return endDate;
    }

    /**
     * Returns the status of the product.
     *
     * @return the status of the product
     */
    public boolean isBought() {
        return isBought;
    }
}
