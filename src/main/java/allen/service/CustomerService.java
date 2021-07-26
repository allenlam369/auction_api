package allen.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import allen.model.Customer;
import allen.repository.CustomerRepository;

@Repository
@Transactional(readOnly = true)
public class CustomerService implements ICustomerService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CustomerRepository repo;

	@Override
	@Transactional
	public Customer save(Customer cust) {
		return repo.save(cust);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Customer> findAll() {
		return repo.findAll();
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Page<Customer> findByLastname(String lastname, Pageable pageable) {
		return repo.findByLastname(lastname, pageable);
	}

}
