package ua.nure.apiclient.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.apiclient.model.core.CartMember;
import ua.nure.apiclient.model.session.AuthToken;
import ua.nure.apiclient.parser.CartMemberParser;
import ua.nure.apiclient.request.GetRequestThread;
import ua.nure.apiclient.request.PostPutRequestThread;

/**
 * This is a service that is used to manage the cart members.
 */
public class MembersService {

    private final String suffix = "/api/CartMember";
    private final String baseUrl;
    private final String authToken;
    private final CartMemberParser memberParser = new CartMemberParser();

    /**
     * This is the constructor of the service.
     *
     * @param token   The authentication token.
     * @param baseUrl The base URL.
     */
    public MembersService(AuthToken token, String baseUrl) {
        checkNotNull(token, "The token cannot be null.");
        checkNotNull(baseUrl, "The base URL cannot be null.");
        this.baseUrl = baseUrl;
        this.authToken = token.token();
    }

    /**
     * This method is used to get all the members.
     *
     * @return The list of members.
     */
    public List<CartMember> getAllMembers() throws InterruptedException {
        String url = baseUrl + suffix + "/getAll";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        return memberParser.parseMembersInCart(response);
    }

    /**
     * This method is used to get all the members by cart id.
     *
     * @param cartId The cart id.
     * @return The list of members.
     */
    public List<CartMember> getMembersByCartId(String cartId) throws InterruptedException {
        String url = baseUrl + suffix + "/getByCartId/" + cartId;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);

        GetRequestThread getRequest = new GetRequestThread(url, headers);
        getRequest.start();

        getRequest.join();

        String response = getRequest.getResponse();

        var members = memberParser.parseMembersInCart(response);

        return getMembersName(members);
    }

    private List<CartMember> getMembersName(List<CartMember> members) throws InterruptedException {
        for (CartMember member : members) {
            String url = "http://172.22.22.69:5160/account/get-username-by-id/" + member.memberId();
            Map<String, String> headers = new HashMap<>();

            GetRequestThread getRequest = new GetRequestThread(url, headers);
            getRequest.start();

            getRequest.join();

            String response = getRequest.getResponse();
            member.setName(response);
        }

        return members;
    }

    public boolean addMember(String cartId, String memberEmail) {
        String url = baseUrl + suffix + "/addByGmail";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + authToken);
        headers.put("Content-Type", "application/json");

        String requestBody = String.format("{\"userMail\": \"%s\", \"cartID\": \"%s\"}", memberEmail, cartId);

        PostPutRequestThread requestThread = new PostPutRequestThread(url, requestBody, "POST", headers, "application/json");
        requestThread.start();

        try {
            requestThread.join();
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }
}
