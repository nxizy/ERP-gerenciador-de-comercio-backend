package br.com.infoexpert.gerenciador_de_comercio.employee.controller;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.UpdateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.service.EmployeeService;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee buildEmployee(Long id, String name) {
        return FixtureFactory.createEmployee(id, name);
    }

    @Test
    @DisplayName("POST /api/employees should create employee")
    void shouldCreateEmployee() throws Exception {
        CreateEmployeeRequest request = FixtureFactory.createEmployeeRequest();
        Employee employee = buildEmployee(1L, request.getName());

        when(employeeService.create(any())).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} should update employee")
    void shouldUpdateEmployee() throws Exception {
        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .name("Peter Atualizado")
                .build();

        Employee updatedEmployee = buildEmployee(1L, request.getName());

        when(employeeService.update(eq(1L), any())).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Peter Atualizado"));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} should delete employee")
    void shouldDeleteEmployee() throws Exception {
        doNothing().when(employeeService).delete(1L);

        mockMvc.perform(delete("/api/employees/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/employees?name=X should return filtered employees by name")
    void shouldReturnEmployeesFilteredByName() throws Exception {
        Employee employee = buildEmployee(1L, "Peter");

        when(employeeService.findEmployees(any(EmployeeFilterDTO.class), any()))
                .thenReturn(new PageImpl<>(List.of(employee)));

        mockMvc.perform(get("/api/employees")
                        .param("name", "Peter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Peter"));
    }

    @Test
    @DisplayName("GET /api/employees?document=000.000.000-00 should return filtered employees by document")
    void shouldReturnEmployeesFilteredByDocument() throws Exception {
        Employee employee = buildEmployee(1L, "Peter");
        employee.setDocument("401.662.290-59");

        when(employeeService.findEmployees(any(EmployeeFilterDTO.class), any()))
                .thenReturn(new PageImpl<>(List.of(employee)));

        mockMvc.perform(get("/api/employees")
                        .param("document", "401.662.290-59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].document").value("401.662.290-59"));
    }

    @Test
    @DisplayName("GET /api/employees?function=technician should return filtered employees by function")
    void shouldReturnEmployeesFilteredByFunction() throws Exception {
        Employee employee = buildEmployee(1L, "Peter");
        employee.setFunction("technician");

        when(employeeService.findEmployees(any(EmployeeFilterDTO.class), any()))
                .thenReturn(new PageImpl<>(List.of(employee)));

        mockMvc.perform(get("/api/employees")
                        .param("function", "technician"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].function").value("technician"));
    }

    @Test
    @DisplayName("GET /api/employees should return all employees if no filter is given")
    void shouldReturnAllEmployeesIfNoFilter() throws Exception {
        Employee employee = buildEmployee(1L, "Peter");

        when(employeeService.findAll(any()))
                .thenReturn(new PageImpl<>(List.of(employee)));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/employees/{id} should return a single employee")
    void shouldReturnEmployeeById() throws Exception {
        Employee employee = buildEmployee(1L, "Peter");

        when(employeeService.findById(eq(1L))).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Peter"));
    }
}
