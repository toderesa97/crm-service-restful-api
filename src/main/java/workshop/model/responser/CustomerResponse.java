package workshop.model.responser;

import workshop.model.customer.Customer;

public class CustomerResponse extends Response implements Cloneable {

    private Customer customer;

    public CustomerResponse() {

    }

    public CustomerResponse(int code, String description, Customer customer) {
        super(code, description);
        this.customer = customer;
    }

    public CustomerResponse(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
