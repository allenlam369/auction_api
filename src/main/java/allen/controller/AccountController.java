package allen.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import allen.model.Account;
import allen.repository.AccountRepository;

@RestController
public class AccountController {
	private final AccountRepository repo;

	public AccountController(AccountRepository repo) {
		this.repo = repo;
	}

	@GetMapping("/accounts")
	List<Account> all() {
		return repo.findAll();
	}

	@GetMapping("/accounts/{id}")
	EntityModel<Account> one(@PathVariable Long id) {
		Account acc = repo.findById(id) //
				.orElseThrow(() -> new ObjectNotFoundException(id, "Account"));

		return EntityModel.of(acc, //
				linkTo(methodOn(AccountController.class).one(id)).withSelfRel(),
				linkTo(methodOn(AccountController.class).all()).withRel("accounts"));

	}
}
