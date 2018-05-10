package workshop.model.responser;

import workshop.model.user.User;

public class UserResponse extends Response implements Cloneable {

    private User user;

    public UserResponse(int code, String description, User user) {
        super(code, description);
        this.user = user;
    }

    public UserResponse(User user) {
        this.user = user;
    }

    public UserResponse() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
