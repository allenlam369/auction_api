package allen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import allen.model.Customer;

@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Page<Customer> findByLastname(String lastname, Pageable pageable);

	Page<Customer> findAll(Pageable pageable);

}
