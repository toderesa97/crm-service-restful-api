package workshop.model;

public class Request {
    // every request must have a token. This serves for authenticating a user!
    private String token;
    private Person person;
    private Customer customer;

    public Request(String token, Person person) {
        this.token = token;
        this.person = person;
    }

    public Request() {

    }
    public Person getPerson() {
        return person;
    }

    public String getToken() {
        return token;
    }

    public Customer getCustomer() {
        return customer;
    }
}
