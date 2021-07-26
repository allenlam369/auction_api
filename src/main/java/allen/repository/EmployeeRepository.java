package allen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import allen.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
