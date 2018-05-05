package workshop.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.model.CryptoUtility;
import workshop.model.Person;
import workshop.repositories.DatabasePersonRepository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ApiAction {
    private DatabasePersonRepository dbRepo;
    private PersonFilterer personFilterer;

    @Autowired
    public ApiAction(DatabasePersonRepository dbRepo, PersonFilterer personFilterer) {
        this.dbRepo =dbRepo;
        this.personFilterer = personFilterer;
    }

    public List<Person> execute() {
        return dbRepo.findAll();
    }

    public boolean insert (Person person) {
        return dbRepo.save(person) != null;
    }

    public void remove(String username) {
        dbRepo.delete(new Person(username));
    }

    public Person getPerson(String username) {
        return dbRepo.findOne(username);
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
        return dbRepo.findByToken(token);
    }

    public boolean userExist(String username) {
        return dbRepo.exists(username);
    }

    public void updateToken (String username, String token) {
        Person person = dbRepo.findOne(username);
        person.setToken(token);
        dbRepo.delete(username);
        dbRepo.save(person);
    }

    public boolean isAdmin(String token) {
        Person person = findByToken (token);
        return person != null && person.isAdmin();
    }

}
