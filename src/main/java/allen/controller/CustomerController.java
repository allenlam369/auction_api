package allen.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import allen.model.Customer;
import allen.repository.CustomerRepository;

@RestController
public class CustomerController {
	private final CustomerRepository repo;

	public CustomerController(CustomerRepository repo) {
		this.repo = repo;
	}

	@GetMapping("/customers")
	List<Customer> all() {
		return repo.findAll();
	}

	@GetMapping("/customers/{id}")
	EntityModel<Customer> one(@PathVariable Long id) {
		Customer cus = repo.findById(id) //
				.orElseThrow(() -> new ObjectNotFoundException(id, "Customer"));

		return EntityModel.of(cus, //
				linkTo(methodOn(CustomerController.class).one(id)).withSelfRel(),
				linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
	}

}
