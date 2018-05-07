package workshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.model.user.User;

@Repository
public interface DatabaseUserRepository extends JpaRepository<User,String> {

    User findByToken(String token);

}
