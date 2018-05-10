package workshop.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="user", schema = "public")
public class User {
    @Id
    private final String username;
    private final String name;
    private String password;
    private String token;
    private String role;
    private String last_person_who_modified;

    public User() {
        username = "";
        name = "";
        token = "";
    }

    public User(String username) {
        this.username = username;
        name = "";
    }

    public String getLast_person_who_modified() {
        return last_person_who_modified;
    }

    public void setLast_person_who_modified(String last_person_who_modified) {
        this.last_person_who_modified = last_person_who_modified;
    }

    @JsonIgnore
    public String getRole() {
        return role;
    }

    @JsonIgnore
    public boolean isAdmin () {
        return role.equals("ADMIN");
    }

    public User(String username, String name, String pass, String role, String last_person_who_modified) {
        this.username = username;
        this.name = name;
        password = pass;
        this.role = role;
        this.last_person_who_modified = last_person_who_modified;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
