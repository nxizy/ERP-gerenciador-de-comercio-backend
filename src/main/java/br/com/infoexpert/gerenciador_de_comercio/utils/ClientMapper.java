package br.com.infoexpert.gerenciador_de_comercio.utils;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ClientMapper {

    public static Page<ClientResponse> toClientResponsePage(Page<Client> clients) {
        return clients.map(Client::toClientResponse);
    }
}
