package br.com.infoexpert.gerenciador_de_comercio.client.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Client Response Object")
public class ClientResponse {

    @Schema(description = "Client's Id", example = "1L")
    private Long id;

    @Schema(description = "Client's name", example = "John doe")
    private String name;

    @Schema(description = "Client's type", example = "LEGAL")
    private ClientType type;

    @Schema(description = "Client's address", example = "{\n" +
            "  \"address\": \"Rua das Flores, 123\",\n" +
            "  \"district\": \"Centro\",\n" +
            "  \"city\": \"São Paulo\",\n" +
            "  \"uf\": \"SP\",\n" +
            "  \"CEP\": \"01234-567\"\n" +
            "}")
    private AddressDTO address;

    @Schema(description = "Client's document", example = "111.111.111-11")
    private String document;

    @Schema(description = "Client's state registration", example = "111.111.111.111")
    private String stateRegistration;

    @Schema(description = "Client's contact", example = "John")
    private String contactName;

    @Schema(description = "Client's phone number", example = "(11) 99999-9999")
    private String phoneNumber;

    @Schema(description = "Client's another phone number", example = "(11) 99999-9999")
    private String phoneNumber2;

}
