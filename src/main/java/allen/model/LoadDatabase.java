package allen.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import allen.repository.AccountRepository;
import allen.repository.CustomerRepository;
import allen.repository.EmployeeRepository;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	// all CommandLineRunner beans will run when the app context is loaded
	@Bean
	CommandLineRunner initDb1(CustomerRepository repo) {
		return args -> {
			log.info("pre-loading " + repo.save(new Customer("AA", "aa")));
			log.info("pre-loading " + repo.save(new Customer("BB", "bb")));
			log.info("pre-loading " + repo.save(new Customer("CC", "cc")));
		};
	}
	
	@Bean
	CommandLineRunner initDb2(AccountRepository repo) {
		return args -> {
			log.info("pre-loading " + repo.save(new Account(3L)));
			log.info("pre-loading " + repo.save(new Account(4L)));
			log.info("pre-loading " + repo.save(new Account(3L)));
		};
	}
	
	@Bean
	CommandLineRunner initDb3(EmployeeRepository repo) {
		return args -> {
			log.info("pre-loading " + repo.save(new Employee("AA", "staff1")));
			log.info("pre-loading " + repo.save(new Employee("BB", "staff2")));
		};
	}
	

	
	
}
