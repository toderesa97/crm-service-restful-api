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
public class ApiAction {

    private DatabaseUserRepository userRepository;
    private DatabaseCustomerRepository customerRepository;
    private PersonFilterer personFilterer;

    @Autowired
    public ApiAction(DatabaseUserRepository userRepository, DatabaseCustomerRepository customerRepository,
                     PersonFilterer personFilterer) {
        this.userRepository = userRepository;
        this.personFilterer = personFilterer;
        this.customerRepository = customerRepository;
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

    public String getToken (String username) {
        return getUserByUsername(username).getToken();
    }

    public boolean validCredentials(String username, String password) {
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

    public void updateToken (String username, String token) {
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
        return token == null || userRepository.findByToken(token) == null;
    }

    public void removeCustomer (long id) {
        customerRepository.delete((int) id);
    }

    public Customer findCustomerById (long id) {
        return customerRepository.findOne((int) id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean addCustomer(Customer customer) {
        return customerRepository.save(customer) != null;
    }
}
