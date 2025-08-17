package br.com.infoexpert.gerenciador_de_comercio.client.controller;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.CLIENT;
import static br.com.infoexpert.gerenciador_de_comercio.utils.ClientMapper.toClientResponsePage;

@RestController
@RequestMapping(CLIENT)
@RequiredArgsConstructor
@Tag(name= "Client's Management", description = "Operations for creating, updating and retrieving clients")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a new client")
    @PostMapping()
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        var client = clientService.create(request);
        return ResponseEntity.ok(client.toClientResponse());
    }

    @Operation(summary = "Update an client")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @Valid @RequestBody UpdateClientRequest request) {
        var client = clientService.update(id, request);
        return ResponseEntity.ok(client.toClientResponse());
    }

    @Operation(summary = "Delete an client")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get clients with optional filters")
    @GetMapping()
    public ResponseEntity<Page<ClientResponse>> getClients(ClientFilterDTO clientFilterDTO, Pageable pageable ) {
        boolean noFilters = clientFilterDTO.isAllNull();

        Page<Client> clients;
        if (noFilters) {
            clients = clientService.findAll(pageable);
        } else {
            clients = clientService.findClients(clientFilterDTO, pageable);
        }

        return ResponseEntity.ok(toClientResponsePage(clients));
    }

    @Operation(summary = "Get client by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        var client = clientService.findById(id);
        return ResponseEntity.ok(client.toClientResponse());
    }

}
