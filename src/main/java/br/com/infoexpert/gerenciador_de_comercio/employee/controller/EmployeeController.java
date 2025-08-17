package br.com.infoexpert.gerenciador_de_comercio.employee.controller;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeResponse;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.UpdateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.EMPLOYEES;
import static br.com.infoexpert.gerenciador_de_comercio.utils.EmployeeMapper.toEmployeeResponsePage;

@RestController
@RequestMapping(EMPLOYEES)
@RequiredArgsConstructor
@Tag(name = "Employee's Management", description = "Operations for creating, updating and retrieving employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        var employee = employeeService.create(request);
        return ResponseEntity.ok(employee.toEmployeeResponse());
    }

    @Operation(summary = "Updating an employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,@Valid @RequestBody UpdateEmployeeRequest request) {
        var employee = employeeService.update(id, request);
        return ResponseEntity.ok(employee.toEmployeeResponse());
    }


    @Operation(summary = "Deleting an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get employees with optional filters ")
    @GetMapping()
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(EmployeeFilterDTO employeeFilterDTO, Pageable pageable) {
        boolean noFilters = employeeFilterDTO.isAllNull();

        Page<Employee> employees;
        if(noFilters) {
            employees = employeeService.findAll(pageable);
        } else {
            employees = employeeService.findEmployees(employeeFilterDTO, pageable);
        }
        return ResponseEntity.ok(toEmployeeResponsePage(employees));
    }

    @Operation(summary = "Get employee by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        var employee = employeeService.findById(id);
        return ResponseEntity.ok(employee.toEmployeeResponse());
    }

}
