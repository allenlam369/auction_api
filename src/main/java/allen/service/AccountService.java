package allen.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import allen.model.Account;
import allen.repository.AccountRepository;

@Repository
@Transactional(readOnly = true)
public class AccountService implements IAccountService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AccountRepository repo;

	@Override
	@Transactional
	public Account save(Account acc) {
		return repo.save(acc);
	}

//	@Override
//	public List<Account> findByCustomer(Customer cust) {
//		return repo.findByCustomer(cust);
//
////		String sql = "select a from Account a where a.customer = ?1";
////		TypedQuery query = em.createQuery(sql, Account.class);
////		query.setParameter(1, cust);
////		return query.getResultList();
//	}

	@Override
	public List<Account> findByCustomerId(Long customerId) {
		return repo.findByCustomerId(customerId);
	}

}
