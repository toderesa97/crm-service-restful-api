package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshop.actions.ApiAction;
import workshop.model.*;
import workshop.model.responser.Response;
import workshop.model.responser.ResponseManager;
import workshop.model.responser.ResponseType;
import workshop.model.user.User;
import workshop.model.user.UserRequest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController {

    private ApiAction action;

    @Autowired
    public UserController(ApiAction apiAction) {
        this.action = apiAction;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/login")
    public Response login(@RequestBody LoginCredentials loginCredentials) throws NoSuchAlgorithmException {
        if (action.validCredentials(loginCredentials.getUsername(), loginCredentials.getPassword())) {
            String token = CryptoUtility.getRandomToken();
            action.updateToken(loginCredentials.getUsername(), token);
            return new Response(200, "Verified", token, new User(loginCredentials.getUsername()));
        }

        return ResponseManager.getResponse(ResponseType.UNAUTHORIZED);
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users/add")
    public Response insert(@RequestBody UserRequest userRequest) throws NoSuchAlgorithmException {

        if (userRequest.getToken() == null) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        if (! action.isAdmin(userRequest.getToken()) ) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        if ( action.userExist(userRequest.getUser().getUsername()) ) {
            return ResponseManager.getResponse(ResponseType.CONFLICT);
        }
        User userToBeAdded = userRequest.getUser();
        userToBeAdded.setPassword(CryptoUtility.getDigest("SHA-256", userRequest.getUser().getPassword()));
        userToBeAdded.setLast_person_who_modified(action.findByToken(userRequest.getToken()).getUsername());

        if (action.addUser(userToBeAdded)) {
            return new Response(200, "OK", userRequest.getUser());
        } else {
            return ResponseManager.getResponse(ResponseType.INTERNAL_ERROR);
        }
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users")
    public List<User> getAllUsers(@RequestBody UserRequest userRequest) {
        String token = userRequest.getToken();
        if ( token == null ) {
            ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        if ( action.isAdmin(userRequest.getToken()) ) {
            return action.getAllUsers();
        } else {
            return null;
        }
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users/remove")
    public Response remove(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest.getToken());
        if ( action.isAdmin(userRequest.getToken()) ) {
            action.removeUserByUsername(userRequest.getUser().getUsername());
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        } else {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
    }

}
