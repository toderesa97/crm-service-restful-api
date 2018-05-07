package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import workshop.actions.ApiAction;
import workshop.model.customer.Customer;
import workshop.model.customer.CustomerRequest;
import workshop.model.responser.Response;
import workshop.model.responser.ResponseManager;
import workshop.model.responser.ResponseType;

import java.util.List;

@RestController
public class CustomerController {

    private ApiAction action;

    @Autowired
    public CustomerController(ApiAction apiAction) {
        action = apiAction;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers")
    public List<Customer> findAllCustomers (@RequestBody CustomerRequest request) {
        if (action.isRegisteredUser(request.getToken())) {
            return null;
        }
        return action.getAllCustomers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/customers/get")
    public Response findCustomer(@RequestBody CustomerRequest customerRequest) {
        if (action.isRegisteredUser(customerRequest.getToken())) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer wantedCustomer = null;
        try {
            wantedCustomer = action.findCustomerById(customerRequest.getCustomer().getId());
        } catch (NullPointerException ignored) {
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS);
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers/add")
    public Response addCustomer (@RequestBody CustomerRequest request) {
        if (action.isRegisteredUser(request.getToken())) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        if (! action.addCustomer(request.getCustomer())) {
            ResponseManager.getResponse(ResponseType.INTERNAL_ERROR);
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/remove")
    public Response removeCustomer (@RequestBody CustomerRequest customerRequest) {
        if (action.isRegisteredUser(customerRequest.getToken())) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        try {
            action.removeCustomer(customerRequest.getCustomer().getId());
        } catch (NullPointerException npe) {
            return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS);
    }

}
