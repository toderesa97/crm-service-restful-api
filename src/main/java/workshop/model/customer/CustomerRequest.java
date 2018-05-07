package workshop.model.customer;

public class CustomerRequest {

    private String token;
    private Customer customer;

    public CustomerRequest(String token, Customer customer) {
        this.token = token;
        this.customer = customer;
    }

    public CustomerRequest() {

    }

    public String getToken() {
        return token;
    }

    public Customer getCustomer() {
        return customer;
    }
}
