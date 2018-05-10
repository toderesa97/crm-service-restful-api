package workshop.model.responser;

import workshop.model.customer.Customer;
import workshop.model.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ResponseManager {

    private static final Map<ResponseType, Supplier<Response>> RESPONSES = new HashMap<ResponseType, Supplier<Response>>() {
        {
            put(ResponseType.SUCCESS, () -> new Response(200, "OK"));
            put(ResponseType.FORBIDDEN, () -> new Response(403, "FORBIDDEN"));
            put(ResponseType.NOT_FOUND, () -> new Response(404, "NOT FOUND: the id may not exist "));
            put(ResponseType.UNAUTHORIZED, () -> new Response(401, "UNAUTHORIZED: tried to operate on a protected resource without providing the proper authentication credentials"));
            put(ResponseType.CREATED, () -> new Response(201, "CREATED"));
            put(ResponseType.INTERNAL_ERROR, () ->new Response(500, "INTERNAL ERROR"));
            put(ResponseType.CONFLICT, () -> new Response(409, "CONFLICT: probably due to duplicate records"));
        }
    };

    public static Response getResponse (ResponseType responseType) {
        return RESPONSES.getOrDefault(responseType, () -> null).get();
    }

    public static Response getResponse(ResponseType responseType, Customer customer) {
        Response response = getResponse(responseType);
        return new CustomerResponse(response.getCode(), response.getDescription(), customer);
    }

    public static Response getResponse(ResponseType responseType, User user) {
        Response response = getResponse(responseType);
        return new UserResponse(response.getCode(), response.getDescription(), user);
    }

    public static Response getResponse(ResponseType responseType, String token) {
        Response response = getResponse(responseType);
        return new Response(response.getCode(), response.getDescription(), token);
    }
}
