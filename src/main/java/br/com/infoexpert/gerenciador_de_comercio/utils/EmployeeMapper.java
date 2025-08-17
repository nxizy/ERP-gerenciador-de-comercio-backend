package br.com.infoexpert.gerenciador_de_comercio.utils;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

public class EmployeeMapper {
    public static Page<EmployeeResponse> toEmployeeResponsePage(Page<Employee> employees) {
        return employees.map(Employee::toEmployeeResponse);
    }
}
