package workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import secure.CryptoUtility;
import workshop.actions.UserAction;
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

    private UserAction userAction;

    @Autowired
    public UserController(UserAction userAction) {
        this.userAction = userAction;
    }

    @RequestMapping(method=RequestMethod.POST, value = "/login")
    public Response login(@RequestBody LoginCredentials loginCredentials) {
        if (userAction.validUserCredentials(loginCredentials.getUsername(), loginCredentials.getPassword())) {
            String token = CryptoUtility.getRandomToken();
            userAction.updateToken(loginCredentials.getUsername(), token);
            return ResponseManager.getResponse(ResponseType.SUCCESS, token);
        }
        return ResponseManager.getResponse(ResponseType.UNAUTHORIZED);
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users/add")
    public Response insert(@RequestBody UserRequest userRequest) {
        if (userRequest.getToken() == null) return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        if (! userAction.isAdmin(userRequest.getToken()) ) {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
        if ( userAction.userExist(userRequest.getUser().getUsername()) ) {
            return ResponseManager.getResponse(ResponseType.CONFLICT);
        }
        User userToBeAdded = userRequest.getUser();
        userToBeAdded.setPassword(CryptoUtility.getDigest(userRequest.getUser().getPassword()));
        userToBeAdded.setLast_person_who_modified(userAction.findByToken(userRequest.getToken()).getUsername());

        if (userAction.addUser(userToBeAdded)) {
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        } else return ResponseManager.getResponse(ResponseType.INTERNAL_ERROR);
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users")
    public List<User> getAllUsers(@RequestBody UserRequest userRequest) {
        String token = userRequest.getToken();
        if ( token == null ) {
            ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        if ( userAction.isAdmin(userRequest.getToken()) ) {
            return userAction.getAllUsers();
        } else {
            return null;
        }
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users/remove")
    public Response remove(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest.getToken());
        if ( userAction.isAdmin(userRequest.getToken()) ) {
            userAction.removeUserByUsername(userRequest.getUser().getUsername());
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        } else {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
    }

}
