package br.com.infoexpert.gerenciador_de_comercio.owner.model;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.OwnerResponse;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.UpdateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.utils.CnpjValidator;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Embedded
    private AddressDTO address;

    @Column(length = 20, unique = true)
    private String document;

    private String phoneNumber;

    private String phoneNumber2;

    public void merge(UpdateOwnerRequest request) {
        if (request.getName() != null) this.name = request.getName();
        if (request.getPassword() != null) this.password = request.getPassword();
        if (request.getDocument() != null) this.document = sanitize(request.getDocument());
        if (request.getAddress() != null) this.address = request.getAddress();
        if (request.getPhoneNumber() != null) this.phoneNumber = sanitize(request.getPhoneNumber());
        if (request.getPhoneNumber2() != null) this.phoneNumber2 = sanitize(request.getPhoneNumber2());
    }

    public static String sanitize(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }

    public void validateDocument(){
        String doc = sanitize(document);
        if (!CnpjValidator.isValid(doc)) {
            throw new RuntimeException("CNPJ invalid.");
        }
    }

    public OwnerResponse toOwnerResponse(){
        return OwnerResponse.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .document(this.document)
                .phoneNumber(this.phoneNumber)
                .phoneNumber2(this.phoneNumber2)
                .build();
    }

}
