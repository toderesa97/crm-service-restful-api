package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshop.actions.ApiAction;
import workshop.model.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class Controller {

    private ApiAction action;

    @Autowired
    public Controller(ApiAction apiAction) {
        this.action = apiAction;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers")
    public List<Customer> findAllCustomers (@RequestBody Request request) {
        if ( ! action.IsRegisteredUser(request.getToken())) {
            return null;
        }
        return action.getAllCustomers();
    }

    @RequestMapping(method=RequestMethod.POST, value = "/api/customers/add")
    public Response addCustomer (@RequestBody Request request) {
        if ( ! action.IsRegisteredUser(request.getToken())) {
            return new Response(400, "Forbidden");
        }
        if (! action.addCustomer(request.getCustomer())) {
            return null;
        }
        return new Response(200, "OK", request.getCustomer());
    }

    @RequestMapping(method=RequestMethod.POST, value = "/login")
    public Response login(@RequestBody LoginCredentials loginCredentials) throws NoSuchAlgorithmException {
        if (action.validCredentials(loginCredentials.getUsername(), loginCredentials.getPassword(),
                loginCredentials.getAuthentication())) {
            String token = CryptoUtility.getRandomToken();
            action.updateToken(loginCredentials.getUsername(), token);
            return new Response(200, "Verified", token, new Person(loginCredentials.getUsername()));
        }

        return new Response(402, "Could not verify the provided credentials");
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/people/add")
    public Response insert(@RequestBody Request request) throws NoSuchAlgorithmException {

        if (request.getToken() == null) {
            return new Response(400, "Forbidden");
        }
        if (! action.isAdmin(request.getToken()) ) {
            return new Response(400, "Forbidden");
        }
        if ( action.userExist(request.getPerson().getUsername()) ) {
            return new Response(350, "Username already in use");
        }
        Person personToBeAdded = request.getPerson();
        personToBeAdded.setPassword(CryptoUtility.getDigest("SHA-256", request.getPerson().getPassword()));
        personToBeAdded.setLast_person_who_modified(action.findByToken(request.getToken()).getUsername());

        if (action.insert(personToBeAdded)) {
            return new Response(200, "OK", request.getPerson());
        } else {
            return new Response(500, "Error whilst inserting");
        }
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/people/remove")
    public Response remove(@RequestBody Request request) {
        System.out.println(request.getToken());
        if ( action.isAdmin(request.getToken()) ) {
            action.remove(request.getPerson().getUsername());
            return new Response(200, "Deleted");
        } else {
            return new Response(400, "Forbidden");
        }
    }

}
