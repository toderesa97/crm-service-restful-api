package workshop.model.customer;

import javax.persistence.*;

@Entity
@Table(name="customer", schema = "public")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String photoURL;
    private String name;
    private String surname;
    private String last_person_who_modified;

    public Customer() {

    }

    public Customer(String name, String surname, String photoURL) {
        this.photoURL = photoURL;
        this.name = name;
        this.surname = surname;
    }

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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

    public String getLast_person_who_modified() {
        return last_person_who_modified;
    }

    public void setLast_person_who_modified(String last_person_who_modified) {
        this.last_person_who_modified = last_person_who_modified;
    }

    public void setId(long id) {
        this.id = id;
    }

}
