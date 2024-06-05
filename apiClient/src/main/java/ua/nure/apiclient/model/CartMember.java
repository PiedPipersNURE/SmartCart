package ua.nure.apiclient.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a user that is a member of a specified cart. It is used to store information about
 * the user and his role in the cart.
 */
public class CartMember {

    /**
     * The unique identifier of the member.
     */
    private final String memberId;

    /**
     * The unique identifier of the cart.
     */
    private final String cartId;

    /**
     * The unique identifier of member in the cart.
     */
    private final String cartMemberId;

    /**
     * The name of the member.
     */
    private final String name;

    public CartMember(String memberId, String cartId, String cartMemberId, String name) {


        checkNotNull(memberId, "Member id cannot be null");
        checkNotNull(cartId, "Cart id cannot be null");
        checkNotNull(cartMemberId, "Cart member id cannot be null");
        checkNotNull(name, "Name cannot be null");

        checkArgument(!memberId.isEmpty(), "Member id cannot be empty");
        checkArgument(!cartId.isEmpty(), "Cart id cannot be empty");
        checkArgument(!cartMemberId.isEmpty(), "Cart member id cannot be empty");
        checkArgument(!name.isEmpty(), "Name cannot be empty");

        this.memberId = memberId;
        this.cartId = cartId;
        this.cartMemberId = cartMemberId;
        this.name = name;
    }

    /**
     * Returns the unique identifier of the member.
     *
     * @return the unique identifier of the member
     */
    public String memberId() {
        return memberId;
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
     * Returns the unique identifier of member in the cart.
     *
     * @return the unique identifier of member in the cart
     */
    public String cartMemberId() {
        return cartMemberId;
    }

    /**
     * Returns the name of the member.
     *
     * @return the name of the member
     */
    public String name() {
        return name;
    }
}
