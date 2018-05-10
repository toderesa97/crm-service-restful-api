package workshop.model.responser;

import workshop.model.user.User;

public class UserResponse extends Response implements Cloneable {

    private User user;

    public UserResponse(int code, String description, User user) {
        super(code, description);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
