package br.com.infoexpert.gerenciador_de_comercio.owner.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOwnerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private AddressDTO address;

    private String document;

    private String phoneNumber;

    private String phoneNumber2;
}
