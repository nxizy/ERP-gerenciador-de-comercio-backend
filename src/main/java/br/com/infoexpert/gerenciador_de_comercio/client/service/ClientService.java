package br.com.infoexpert.gerenciador_de_comercio.client.service;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClientService {
    Client create(CreateClientRequest request);

    Client update(Long id, UpdateClientRequest request);

    void delete(Long id);

    Page<Client> findAll(Pageable pageable);

    Client findById(Long id);

    Page<Client> findClients(ClientFilterDTO clientFilterDTO, Pageable pageable);
}
