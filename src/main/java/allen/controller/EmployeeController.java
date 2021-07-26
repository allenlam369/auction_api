package allen.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import allen.model.Employee;
import allen.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	private final EmployeeRepository repo;

	// repo is injected by constructor into the controller
	public EmployeeController(EmployeeRepository repo) {
		this.repo = repo;
	}

	@GetMapping("/employees")
	List<Employee> all() {
		return repo.findAll();
	}

	@PostMapping("/employees")
	Employee newEmploee(@RequestBody Employee newEmployee) {
		return repo.save(newEmployee);
	}

//	@GetMapping("/employees/{id}")
//	Employee one(@PathVariable Long id) {
//		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//	}

	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable Long id) {

		Employee employee = repo.findById(id) //
				.orElseThrow(() -> new ObjectNotFoundException(id, "Employee"));

		return EntityModel.of(employee, //
				linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return repo.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repo.save(employee);

		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repo.save(newEmployee);
		});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repo.deleteById(id);
	}
}
