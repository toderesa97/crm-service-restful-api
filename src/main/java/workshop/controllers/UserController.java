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
        System.out.println(CryptoUtility.getDigest(loginCredentials.getPassword()));
        if (userAction.validUserCredentials(loginCredentials.getUsername(), loginCredentials.getPassword())) {
            String token = CryptoUtility.getRandomToken();
            userAction.updateToken(loginCredentials.getUsername(), token);
            return ResponseManager.getResponse(ResponseType.SUCCESS, token);
        }
        return ResponseManager.getResponse(ResponseType.UNAUTHORIZED);
    }

    @RequestMapping(method=RequestMethod.POST, value="/api/users/add")
    public Response addUser(@RequestBody UserRequest userRequest) {
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
    public Response removeUser(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest.getToken());
        if ( userAction.isAdmin(userRequest.getToken()) ) {
            userAction.removeUserByUsername(userRequest.getUser().getUsername());
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        } else {
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/users/get")
    public Response getUser(@RequestBody UserRequest userRequest) {
        if ( userAction.isAdmin(userRequest.getToken()) ) {
            if (userRequest.getToken() == null) {
                return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
            }
            User wanted = userAction.getUserByUsername(userRequest.getUser().getUsername());
            if (wanted == null)
                return ResponseManager.getResponse(ResponseType.NOT_FOUND);
            return ResponseManager.getResponse(ResponseType.SUCCESS, wanted);
        }
        return ResponseManager.getResponse(ResponseType.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/users/update")
    public Response editUser(@RequestBody UserRequest userRequest) {
        if (! userAction.isAdmin(userRequest.getToken()))
            return ResponseManager.getResponse(ResponseType.FORBIDDEN);
        User userToEdit = userRequest.getUser();
        if ( userToEdit == null ) {
            return ResponseManager.getResponse(ResponseType.BAD_REQUEST);
        }
        String lastUserWhoModified = userAction.findByToken(userRequest.getToken()).getUsername();
        if (userAction.updateUser(userToEdit, userToEdit.getUsername(), lastUserWhoModified)) {
            return ResponseManager.getResponse(ResponseType.SUCCESS);
        }
        return ResponseManager.getResponse(ResponseType.NOT_FOUND);
    }

}
