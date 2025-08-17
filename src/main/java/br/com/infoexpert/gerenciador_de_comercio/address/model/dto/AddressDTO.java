package br.com.infoexpert.gerenciador_de_comercio.address.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    @NotBlank
    private String address;

    private String district;

    private String city;

    private String CEP;

    private String complement;

    private UF uf;

}
