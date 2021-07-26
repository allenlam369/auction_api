package allen.service;

import java.util.List;

import allen.model.Account;

public interface IAccountService {

	Account save(Account acc);

	List<Account> findByCustomerId(Long customerId);

}
