package allen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import allen.model.Customer;

public interface ICustomerService {

	List<Customer> findAll();

	Customer save(Customer cust);

	Page<Customer> findAll(Pageable pageable);

	Page<Customer> findByLastname(String lastname, Pageable pageable);

	Optional<Customer> findById(Long id);

}
