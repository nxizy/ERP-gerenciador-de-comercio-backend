package br.com.infoexpert.gerenciador_de_comercio.employee.service;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.UpdateEmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {

    Employee create(CreateEmployeeRequest employee);

    Employee update(Long id, UpdateEmployeeRequest employee);

    Employee findById(Long id);

    Page<Employee> findAll(Pageable pageable);

    void delete(Long id);

    Page<Employee> findEmployees(EmployeeFilterDTO employeeFilterDTO, Pageable pageable);
}
