package ua.nure.apiclient.model.core;

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
    private final String memberID;

    /**
     * The unique identifier of the cart.
     */
    private final String cartID;

    /**
     * The unique identifier of member in the cart.
     */
    private final String cartMemberID;

    /**
     * The name of the member.
     */
    private String name;

    public CartMember(String memberID, String cartID, String cartMemberID, String name) {


        checkNotNull(memberID, "Member id cannot be null");
        checkNotNull(cartID, "Cart id cannot be null");
        checkNotNull(cartMemberID, "Cart member id cannot be null");
        checkNotNull(name, "Name cannot be null");

        checkArgument(!memberID.isEmpty(), "Member id cannot be empty");
        checkArgument(!cartID.isEmpty(), "Cart id cannot be empty");
        checkArgument(!cartMemberID.isEmpty(), "Cart member id cannot be empty");
        checkArgument(!name.isEmpty(), "Name cannot be empty");

        this.memberID = memberID;
        this.cartID = cartID;
        this.cartMemberID = cartMemberID;
        this.name = name;
    }

    public void setName(String name) {
        checkNotNull(name, "Name cannot be null");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        this.name = name;
    }

    /**
     * Returns the unique identifier of the member.
     *
     * @return the unique identifier of the member
     */
    public String memberId() {
        return memberID;
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
     * Returns the unique identifier of member in the cart.
     *
     * @return the unique identifier of member in the cart
     */
    public String cartMemberId() {
        return cartMemberID;
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
