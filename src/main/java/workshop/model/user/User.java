package workshop.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="user", schema = "public")
public class User {
    @Id
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String token;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String last_person_who_modified;
    private String photoURL;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public boolean isAdmin () {
        return role.equals("ADMIN");
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPhotoURL() {
        return photoURL;
    }
}
