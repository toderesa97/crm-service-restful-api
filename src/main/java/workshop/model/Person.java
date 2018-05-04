package workshop.model;

import javax.persistence.*;

@Entity
@Table(name="person")
public class Person {
    @Id
    private final String username;
    private final String name;
    private String password;
    private String token;

    public Person() {
        username = "";
        name = "";
        token = "";
    }

    public Person(String username) {
        this.username = username;
        name = "";
    }

    public Person(String username, String name, String pass) {
        this.username = username;
        this.name = name;
        password = pass;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

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
}
