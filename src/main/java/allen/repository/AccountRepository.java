package allen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import allen.model.Account;

// I do not need a class to implements AccountRepository
// Spring data jpa creates an implementation when I run the application

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByCustomerId(Long customerId);

}
