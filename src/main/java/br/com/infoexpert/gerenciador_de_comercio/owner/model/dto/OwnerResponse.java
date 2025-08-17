package br.com.infoexpert.gerenciador_de_comercio.owner.model.dto;


import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerResponse {

    @Schema(description = "Owner's Id", example = "1L")
    private Long id;

    @Schema(description = "Owner's name", example = "InfoExpert")
    private String name;

    @Schema(description = "Owner's address", example = "{\n" +
            "  \"address\": \"Rua das Flores, 123\",\n" +
            "  \"district\": \"Centro\",\n" +
            "  \"city\": \"São Paulo\",\n" +
            "  \"uf\": \"SP\",\n" +
            "  \"CEP\": \"01234-567\"\n" +
            "}")
    private AddressDTO address;

    @Schema(description = "Owner's document", example = "11.111.111/0001-11")
    private String document;

    @Schema(description = "Owner's phone number", example = "(11) 99999-9999")
    private String phoneNumber;

    @Schema(description = "Owner's another phone number", example = "(11) 99999-9999")
    private String phoneNumber2;
}
