package br.com.infoexpert.gerenciador_de_comercio.address.model;

import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import jakarta.persistence.*;
import lombok.*;


@Embeddable
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String address;

    private String district;

    private String city;

    private String CEP;

    private String complement;

    private UF uf;

}
