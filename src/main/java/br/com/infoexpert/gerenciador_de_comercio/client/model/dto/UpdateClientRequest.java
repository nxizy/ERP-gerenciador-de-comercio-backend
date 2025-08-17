package br.com.infoexpert.gerenciador_de_comercio.client.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import jakarta.validation.Valid;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateClientRequest {

    private String name;

    private ClientType type;

    @Valid
    private AddressDTO address;

    private String document;

    private String stateRegistration;

    private String contactName;

    private String phoneNumber;

    private String phoneNumber2;
}
