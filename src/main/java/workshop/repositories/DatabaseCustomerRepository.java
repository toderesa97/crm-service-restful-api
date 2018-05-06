package workshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import workshop.model.Customer;

public interface DatabaseCustomerRepository extends JpaRepository<Customer, Integer> {

}
