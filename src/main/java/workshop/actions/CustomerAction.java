package workshop.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.model.customer.Customer;
import workshop.repositories.DatabaseCustomerRepository;
import workshop.repositories.DatabaseUserRepository;

import java.util.List;

@Service
public class CustomerAction {

    private DatabaseCustomerRepository customerRepository;

    @Autowired
    public CustomerAction (DatabaseCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public void removeCustomer(long id) {
        customerRepository.delete(id);
    }

    public Customer findCustomerById(long id) {
        return customerRepository.findOne(id);
    }

    public boolean customerExist(long id) {
        return findCustomerById(id) != null;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean addCustomer(Customer customer) {
        return customerRepository.save(customer) != null;
    }

    public boolean updateCustomer(Customer customer, String lastUserWhoModified) {
        Customer customer_to_edit = findCustomerById(customer.getId());
        if (customer_to_edit == null) {
            return false;
        }
        if (customer.getName() != null) {
            customer_to_edit.setName(customer.getName());
        }
        if (customer.getSurname() != null) {
            customer_to_edit.setSurname(customer.getSurname());
        }
        if (customer.getPhotoURL() != null) {
            customer_to_edit.setPhotoURL(customer.getPhotoURL());
        }
        customer_to_edit.setId(customer.getId());
        customer_to_edit.setLast_person_who_modified(lastUserWhoModified);
        return customerRepository.save(customer_to_edit) != null;
    }

    public void updateImageCustomer(Long customerId, String lastPersonWhoModified, String path) {
        Customer customer = findCustomerById(customerId);
        customer.setPhotoURL(path);
        customer.setLast_person_who_modified(lastPersonWhoModified);
        customerRepository.save(customer);
    }

}
