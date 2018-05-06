package workshop.model;

public class Response {

    private int code;
    private String description;
    private Person person;
    private Customer customer;
    private String token;

    public Response(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Response(int code, String description, String token, Person person) {
        this.code = code;
        this.description = description;
        this.token = token;
    }

    public Response(int code, String description, Person person) {
        this.code = code;
        this.description = description;
        this.person = person;
    }

    public Response(int code, String description, Customer customer) {
        this.code = code;
        this.description = description;
        this.customer = customer;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return getCode() == response.getCode();
    }
}
