package workshop.model.responser;

import workshop.model.customer.Customer;
import workshop.model.user.User;

public class Response {

    private int code;
    private String description;
    private User user;
    private Customer customer;
    private String token;

    public Response(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Response(int code, String description, String token, User user) {
        this.code = code;
        this.description = description;
        this.token = token;
    }

    public Response(int code, String description, User user) {
        this.code = code;
        this.description = description;
        this.user = user;
    }

    public Response(int code, String description, Customer customer) {
        this.code = code;
        this.description = description;
        this.customer = customer;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return getCode() == response.getCode();
    }
}
