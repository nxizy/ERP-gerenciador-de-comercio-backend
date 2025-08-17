package br.com.infoexpert.gerenciador_de_comercio.employee.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class EmployeeFilterDTO {
    private String name;
    private String document;
    private String function;

    public boolean isAllNull() {
        return name == null &&
                document == null &&
                function == null;
    }
}
