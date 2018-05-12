package workshop.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secure.CryptoUtility;
import workshop.model.responser.ResponseManager;
import workshop.model.responser.ResponseType;
import workshop.model.user.User;
import workshop.repositories.DatabaseUserRepository;
import java.util.List;

@Service
public class UserAction {

    private DatabaseUserRepository userRepository;
    private PersonFilterer personFilterer;

    @Autowired
    public UserAction(DatabaseUserRepository userRepository, PersonFilterer personFilterer) {
        this.userRepository = userRepository;
        this.personFilterer = personFilterer;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        return userRepository.save(user) != null;
    }

    public void removeUserByUsername(String username) {
        userRepository.delete(new User(username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findOne(username);
    }

    public String getToken(String username) {
        return getUserByUsername(username).getToken();
    }

    public boolean validUserCredentials(String username, String password) {
        try {
            return CryptoUtility.check(getUserByUsername(username).getPassword(), password);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public User findByToken(String token) {
        return userRepository.findUserByToken(token);
    }

    public boolean userExist(String username) {
        return userRepository.exists(username);
    }

    public void updateToken(String username, String token) {
        User user = userRepository.findOne(username);
        user.setToken(token);
        userRepository.delete(username);
        userRepository.save(user);
    }

    public boolean isAdmin(String token) {
        User user = findByToken(token);
        return user != null && user.isAdmin() && tokenIsNotExpired(user, token);
    }

    private boolean tokenIsNotExpired(User user, String token) {
        return true;
    }

    public boolean isRegisteredUser(String token) {
        return token != null && userRepository.findUserByToken(token) != null;
    }

    public boolean updateUser(User userToEdit, String username, String lastUserWhoModified) {
        User currentUser = getUserByUsername(username);
        if (currentUser == null) {
            return false;
        }
        if (userToEdit.getPassword() != null) {
            currentUser.setPassword(CryptoUtility.getDigest(userToEdit.getPassword()));
        }
        String name;
        if ((name = userToEdit.getName()) != null) {
            if (! name.isEmpty()) currentUser.setName(name);
        }
        String role;
        if ((role = userToEdit.getRole()) != null) {
            if (role.equals("ADMIN") || role.equals("USER")) {
                currentUser.setRole(role);
            } else {
                return false;
            }
        }
        currentUser.setLast_person_who_modified(lastUserWhoModified);
        return userRepository.save(currentUser) != null;
    }
}
