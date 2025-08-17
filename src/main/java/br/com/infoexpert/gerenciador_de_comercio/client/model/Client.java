package br.com.infoexpert.gerenciador_de_comercio.client.model;


import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import br.com.infoexpert.gerenciador_de_comercio.utils.CnpjValidator;
import br.com.infoexpert.gerenciador_de_comercio.utils.CpfValidator;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    @Embedded
    private AddressDTO address;

    @Column(length = 20, unique = true)
    private String document;

    private String stateRegistration;

    private String contactName;

    private String phoneNumber;

    private String phoneNumber2;

    public void merge(UpdateClientRequest request) {
        if (request.getName() != null) this.name = request.getName();
        if (request.getType() != null) this.type = request.getType();
        if (request.getAddress() != null) this.address = request.getAddress();
        if (request.getDocument() != null) this.document = sanitize(request.getDocument());
        if (request.getStateRegistration() != null) this.stateRegistration = sanitize(request.getStateRegistration());
        if (request.getContactName() != null) this.contactName = request.getContactName();
        if (request.getPhoneNumber() != null) this.phoneNumber = sanitize(request.getPhoneNumber());
        if (request.getPhoneNumber2() != null) this.phoneNumber2 = sanitize(request.getPhoneNumber2());
    }

    public static String sanitize(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }

    public void validateDocument() {
        String doc = sanitize(this.document);
        if (type == ClientType.PHYSICAL && !CpfValidator.isValid(doc)) {
            throw new RuntimeException("CPF invalid");
        }
        if (type == ClientType.LEGAL && !CnpjValidator.isValid(doc)) {
            throw new RuntimeException("CNPJ invalid.");
        }
    }

    public ClientResponse toClientResponse() {
        return ClientResponse.builder()
                .id(this.id)
                .name(this.name)
                .type(this.type)
                .address(this.address)
                .document(this.document)
                .stateRegistration(this.stateRegistration)
                .contactName(this.contactName)
                .phoneNumber(this.phoneNumber)
                .phoneNumber2(this.phoneNumber2)
                .build();
    }


}
