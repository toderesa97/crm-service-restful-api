package workshop.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.model.CryptoUtility;
import workshop.model.Customer;
import workshop.model.Person;
import workshop.repositories.DatabaseCustomerRepository;
import workshop.repositories.DatabasePersonRepository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ApiAction {
    private DatabasePersonRepository personRepository;
    private DatabaseCustomerRepository customerRepository;
    private PersonFilterer personFilterer;

    @Autowired
    public ApiAction(DatabasePersonRepository personRepository, DatabaseCustomerRepository customerRepository,
                     PersonFilterer personFilterer) {
        this.personRepository = personRepository;
        this.personFilterer = personFilterer;
        this.customerRepository = customerRepository;
    }

    public List<Person> execute() {
        return personRepository.findAll();
    }

    public boolean insert (Person person) {
        return personRepository.save(person) != null;
    }

    public void remove(String username) {
        personRepository.delete(new Person(username));
    }

    public Person getPerson(String username) {
        return personRepository.findOne(username);
    }

    public String getToken (String username) {
        return getPerson(username).getToken();
    }

    public boolean validCredentials(String username, String password, String authenticationMethod) {
        try {
            if (authenticationMethod != null && authenticationMethod.equals("digested")) {
                return getPerson(username).getPassword().equals(password);
            } else {
                return getPerson(username).getPassword().equals(CryptoUtility.getDigest("SHA-256", password));
            }
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            return false;
        }
    }

    public Person findByToken (String token) {
        return personRepository.findByToken(token);
    }

    public boolean userExist(String username) {
        return personRepository.exists(username);
    }

    public void updateToken (String username, String token) {
        Person person = personRepository.findOne(username);
        person.setToken(token);
        personRepository.delete(username);
        personRepository.save(person);
    }

    public boolean isAdmin(String token) {
        Person person = findByToken (token);
        return person != null && person.isAdmin();
    }

    public boolean IsRegisteredUser(String token) {
        return token != null && personRepository.findByToken(token) != null;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean addCustomer(Customer customer) {
        return customerRepository.save(customer) != null;
    }
}
