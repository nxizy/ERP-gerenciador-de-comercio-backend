package br.com.infoexpert.gerenciador_de_comercio.employee.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String document;

    private String function;
}
