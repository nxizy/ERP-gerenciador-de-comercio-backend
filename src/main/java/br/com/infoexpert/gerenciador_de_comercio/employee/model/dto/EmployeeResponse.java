package br.com.infoexpert.gerenciador_de_comercio.employee.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Employee Response Object")
public class EmployeeResponse {

    @Schema(description = "Employee's Id", example = "1L")
    private Long id;

    @Schema(description = "Employee's Name", example = "Neymar")
    private String name;

    @Schema(description = "Employee's document", example = "111.111.111-11")
    private String document;

    @Schema(description = "Employee's function", example = "MotherBoard Technician")
    private String function;
}
