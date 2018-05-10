package workshop.model.responser;

import com.fasterxml.jackson.annotation.JsonGetter;
import workshop.model.customer.Customer;

public class CustomerResponse extends Response {

    private Customer customer;

    public CustomerResponse(int code, String description, Customer customer) {
        super(code, description);
        this.customer = customer;
    }

    @JsonGetter("customer")
    public Customer getCustomer() {
        return customer;
    }

}
