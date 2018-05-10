package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import workshop.actions.CustomerAction;
import workshop.actions.UserAction;
import workshop.model.customer.Customer;
import workshop.model.customer.CustomerRequest;
import workshop.model.responser.CustomerResponse;
import workshop.model.responser.Response;
import workshop.model.responser.ResponseManager;
import workshop.model.responser.ResponseType;

import java.util.List;

@RestController
public class CustomerController {

    private CustomerAction customerAction;
    private UserAction userAction;

    @Autowired
    public CustomerController(CustomerAction customerAction, UserAction userAction) {
        this.userAction = userAction;
        this.customerAction = customerAction;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers")
    public List<Customer> findAllCustomers (@RequestBody CustomerRequest request) {
        if (userAction.isRegisteredUser(request.getToken())) {
            return customerAction.getAllCustomers();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/customers/get")
    public CustomerResponse findCustomer(@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken())) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer wantedCustomer = null;
        try {
            wantedCustomer = customerAction.findCustomerById(customerRequest.getCustomer().getId());
        } catch (NullPointerException ignored) {
        }
        return (CustomerResponse) ResponseManager.getResponse(ResponseType.SUCCESS, wantedCustomer);
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers/add")
    public CustomerResponse addCustomer (@RequestBody CustomerRequest request) {
        String token = request.getToken();
        if (! userAction.isRegisteredUser(token)) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer new_customer = request.getCustomer();
        if (new_customer == null) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        new_customer.setLast_person_who_modified(userAction.findByToken(token).getUsername());
        if (customerAction.addCustomer(new_customer)) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.SUCCESS);
        }
        return (CustomerResponse) ResponseManager.getResponse(ResponseType.INTERNAL_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/remove")
    public CustomerResponse removeCustomer (@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken())) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        try {
            customerAction.removeCustomer(customerRequest.getCustomer().getId());
        } catch (NullPointerException npe) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        return (CustomerResponse) ResponseManager.getResponse(ResponseType.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/update")
    public CustomerResponse editCustomer (@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken())) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer customer = customerRequest.getCustomer();
        if (customer == null) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        String lastUserWhoModified = userAction.findByToken(customerRequest.getToken()).getUsername();
        if (customerAction.updateCustomer(customer, lastUserWhoModified)) {
            return (CustomerResponse) ResponseManager.getResponse(ResponseType.SUCCESS);
        }
        return (CustomerResponse) ResponseManager.getResponse(ResponseType.NOT_FOUND);
    }

}
