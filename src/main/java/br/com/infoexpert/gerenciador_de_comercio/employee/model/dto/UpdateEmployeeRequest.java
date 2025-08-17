package br.com.infoexpert.gerenciador_de_comercio.employee.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeeRequest {

    private String name;

    private String document;

    private String function;
}
