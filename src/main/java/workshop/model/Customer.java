package workshop.model;

import javax.persistence.*;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String photourl;
    private String name;
    private String surname;

    public Customer() {

    }

    public Customer(String name, String surname, String photoURL) {
        this.photourl = photoURL;
        this.name = name;
        this.surname = surname;
    }

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getPhotoURL() {
        return photourl;
    }

    public void setPhotoURL(String photoURL) {
        this.photourl = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }
}
