package workshop.model.user;

public class UserRequest {

    private String token;
    private User user;

    public UserRequest(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public UserRequest() {

    }
    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

}
