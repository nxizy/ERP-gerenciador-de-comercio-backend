package br.com.infoexpert.gerenciador_de_comercio.employee.service;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.UpdateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.repository.EmployeeRepository;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.ConflictException;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(CreateEmployeeRequest request) {
        if(employeeRepository.existsByDocument(request.getDocument())) {
            throw new ConflictException("Document already registered");
        }
        Employee employee = Employee.builder()
                .name(request.getName())
                .document(request.getDocument())
                .function(request.getFunction())
                .build();

        employee.validateDocument();

        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id,UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        employee.merge(request);
        employee.validateDocument();
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        if(!employeeRepository.existsById(id)) {
            throw new NotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findEmployees(EmployeeFilterDTO filter, Pageable pageable) {
        Specification<Employee> spec = (root, query, cb) -> null;

        if(filter.getName() != null) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("name"), filter.getName())));
        }

        if(filter.getFunction() != null) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("function"), filter.getFunction())));
        }

        if(filter.getDocument() != null) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("document"), filter.getDocument())));
        }

        return employeeRepository.findAll(spec, pageable);
    }
}
