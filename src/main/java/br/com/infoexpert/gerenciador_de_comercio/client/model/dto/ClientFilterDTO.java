package br.com.infoexpert.gerenciador_de_comercio.client.model.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.ProtocolFamily;

@Getter
@Setter
@Builder
public class ClientFilterDTO {
    private String name;
    private String phoneNumber;
    private String document;


    public boolean isAllNull() {
        return name == null &&
                phoneNumber == null &&
                document == null;
    }
}
