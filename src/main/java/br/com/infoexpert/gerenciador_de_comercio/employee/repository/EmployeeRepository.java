package br.com.infoexpert.gerenciador_de_comercio.employee.repository;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository <Employee, Long>, JpaSpecificationExecutor<Employee> {
    boolean existsByDocument(@NotBlank String document);
}
