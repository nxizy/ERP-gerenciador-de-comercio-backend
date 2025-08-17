package br.com.infoexpert.gerenciador_de_comercio.employee.model;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeResponse;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.UpdateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.utils.CpfValidator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 20, nullable = false)
    private String document;

    private String function;

    public static String sanitize(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }

    public void validateDocument() {
        String doc = sanitize(this.document);
        if(!CpfValidator.isValid(doc)) {
            throw new RuntimeException("Document is not valid");
        }
    }

    public void merge(UpdateEmployeeRequest request) {
        if (request.getName() != null) this.name = request.getName();
        if (request.getDocument() != null) this.document = sanitize(request.getDocument());
        if (request.getFunction() != null) this.function = request.getFunction();

    }

    public EmployeeResponse toEmployeeResponse() {
        return EmployeeResponse.builder()
                .id(this.id)
                .name(this.name)
                .document(this.document)
                .function(this.function)
                .build();
    }
}
