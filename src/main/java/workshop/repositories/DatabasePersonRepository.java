package workshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.model.Person;

import java.util.List;

@Repository
public interface DatabasePersonRepository extends JpaRepository<Person,String> {

    void removePersonByUsername(String un);

    Person findByUsername(String username);

    Person findByToken(String token);

}
