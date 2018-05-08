package workshop.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.model.CryptoUtility;
import workshop.model.customer.Customer;
import workshop.model.user.User;
import workshop.repositories.DatabaseCustomerRepository;
import workshop.repositories.DatabaseUserRepository;

import java.security.NoSuchAlgorithmException;
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
            return getUserByUsername(username).getPassword().equals(CryptoUtility.getDigest("SHA-256", password));
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            return false;
        }
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
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
        return user != null && user.isAdmin();
    }

    public boolean isRegisteredUser(String token) {
        return token != null && userRepository.findByToken(token) != null;
    }

}
