package br.com.infoexpert.gerenciador_de_comercio.employee.service;


import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.repository.EmployeeRepository;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create Employee")
    void shouldCreateEmployee() {
        CreateEmployeeRequest request = FixtureFactory.createEmployeeRequest();

        Employee savedEmployee = FixtureFactory.createEmployee(1L, "Peter");

        when(employeeRepository.existsByDocument("401.662.290-59")).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee response = employeeService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Peter");

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should return employees filtered by name")
    void shouldReturnEmployeesFilteredByName() {
        EmployeeFilterDTO filter = EmployeeFilterDTO.builder()
                .name("Peter")
                .build();

        Employee employee = FixtureFactory.createEmployee(1L, "Peter");
        Page<Employee> page = new PageImpl<>(List.of(employee));

        // Casting necessário por causa do Specification
        when(employeeRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(page);

        Page<Employee> response = employeeService.findEmployees(filter, PageRequest.of(0, 10));

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getName()).isEqualTo("Peter");
    }

    @Test
    @DisplayName("Should return all employees if no filter")
    void shouldReturnAllEmployeesIfNoFilter() {
        Employee employee = FixtureFactory.createEmployee(1L, "Peter");
        Page<Employee> page = new PageImpl<>(List.of(employee));

        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Employee> response = employeeService.findAll(PageRequest.of(0, 10));

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
    }
}
