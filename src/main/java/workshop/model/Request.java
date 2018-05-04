package workshop.model;

public class Request {
    private String token;
    private Person person;

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
}
