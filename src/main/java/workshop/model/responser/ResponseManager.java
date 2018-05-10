package workshop.model.responser;

import workshop.model.customer.Customer;
import workshop.model.user.User;

import java.util.HashMap;
import java.util.Map;

public class ResponseManager {

    // SUCCESS, FORBIDDEN, CREATED, ACCEPTED, BAD_REQUEST, UNAUTHORIZED, NOT_FOUND;
    private static Map<ResponseType, Response> responses = new HashMap<>();
    private static boolean instancesAlreadyLoaded = false;

    public static Response getResponse (ResponseType responseType) {
        if ( responses.containsKey(responseType) ) {
            return responses.get(responseType);
        }
        if (instancesAlreadyLoaded) {
        } else {
            loadResponses();
            instancesAlreadyLoaded = true;
            return responses.get(responseType);
        }
        return null;
    }

    public static Response getResponse(ResponseType responseType, Customer customer) {
        Response response = null;
        try {
            response = ((CustomerResponse) (getResponse(responseType).clone()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (response != null) {
            ((CustomerResponse) response).setCustomer(customer);
        }
        return response;
    }

    public static Response getResponse(ResponseType responseType, User user) {
        UserResponse response = null;
        try {
            response = (UserResponse) getResponse(responseType).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (response != null) {
            response.setUser(user);
        }
        return response;
    }

    public static Response getResponse(ResponseType responseType, String token) {
        Response response = null;
        try {
            response = (Response) getResponse(responseType).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (response != null) {
            //response.setUser(user);
            response.setToken(token);
        }
        return response;
    }

    private static void loadResponses() {
        Response success = new Response(200, "OK");
        responses.put(ResponseType.SUCCESS, success);
        Response forbidden = new Response(403, "FORBIDDEN");
        responses.put(ResponseType.FORBIDDEN, forbidden);
        Response notFound = new Response(404, "NOT FOUND: the id may not exist ");
        responses.put(ResponseType.NOT_FOUND, notFound);
        Response unauthorized = new Response(401, "UNAUTHORIZED: " +
                "tried to operate on a protected resource without providing the proper authentication credentials");
        responses.put(ResponseType.UNAUTHORIZED, unauthorized);
        Response created = new Response(201, "CREATED");
        responses.put(ResponseType.CREATED, created);
        Response internalError = new Response(500, "INTERNAL ERROR");
        responses.put(ResponseType.INTERNAL_ERROR, internalError);
        Response conflict = new Response(409, "CONFLICT: probably due to duplicate records");
        responses.put(ResponseType.CONFLICT, conflict);
    }


}
