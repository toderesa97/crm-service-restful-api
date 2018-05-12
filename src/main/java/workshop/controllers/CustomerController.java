package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import workshop.actions.CustomerAction;
import workshop.actions.UserAction;
import workshop.model.customer.Customer;
import workshop.model.customer.CustomerRequest;
import workshop.model.responser.Response;
import workshop.model.responser.ResponseManager;
import workshop.model.responser.ResponseType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {

    private final String CUSTOMER_PATH_IMAGES = "C:\\Users\\Public\\Pictures";
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response handleFormUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
                File destination = new File(CUSTOMER_PATH_IMAGES + "\\" + file.getOriginalFilename());
                ImageIO.write(src, "png", destination);
            } catch (IOException e) {
                return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
            }
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/customers/get")
    public Response findCustomer(@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken())) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer wantedCustomer = null;
        try {
            wantedCustomer = customerAction.findCustomerById(customerRequest.getCustomer().getId());
        } catch (NullPointerException ignored) {
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS, wantedCustomer);
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers/add")
    public Response addCustomer (@RequestBody CustomerRequest request) {
        String token = request.getToken();
        if (! userAction.isRegisteredUser(token)) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        Customer new_customer = request.getCustomer();
        if (new_customer == null) {
            return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        new_customer.setLast_person_who_modified(userAction.findByToken(token).getUsername());
        if (customerAction.addCustomer(new_customer)) {
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        }
        return ResponseManager.getResponse(ResponseType.INTERNAL_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/remove")
    public Response removeCustomer (@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken()))
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        try {
            customerAction.removeCustomer(customerRequest.getCustomer().getId());
        } catch (NullPointerException npe) {
            return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        return ResponseManager.getResponse(ResponseType.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/update")
    public Response editCustomer (@RequestBody CustomerRequest customerRequest) {
        if (! userAction.isRegisteredUser(customerRequest.getToken()))
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        Customer customer = customerRequest.getCustomer();
        if (customer == null) {
            return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        String lastUserWhoModified = userAction.findByToken(customerRequest.getToken()).getUsername();
        if (customerAction.updateCustomer(customer, lastUserWhoModified)) {
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        }
        return ResponseManager.getResponse(ResponseType.NOT_FOUND);
    }

}
