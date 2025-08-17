package br.com.infoexpert.gerenciador_de_comercio.client.service;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.repository.ClientRepository;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.ConflictException;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create Client")
    void shouldCreateClient() {
        CreateClientRequest request = FixtureFactory.createClientRequest();

        Client savedClient = FixtureFactory.createClient(1L, "John Doe");

        when(clientRepository.existsByDocument(any())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        Client response = clientService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("John Doe");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Should throw ConflictException when document already exists")
    void shouldThrowConflictWhenDocumentExists() {
        CreateClientRequest request = FixtureFactory.createClientRequest();

        when(clientRepository.existsByDocument("299.552.588-02")).thenReturn(true);

        assertThatThrownBy(() -> clientService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Document already registered");
    }

    @Test
    @DisplayName("Should Update Client")
    void shouldUpdateClient() {
        Client existing = FixtureFactory.createClient(1L, "John Doe");
        UpdateClientRequest update = UpdateClientRequest.builder()
                .name("John Doe Updated")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.save(any(Client.class))).thenReturn(existing);

        Client updated = clientService.update(1L, update);

        assertThat(updated.getName()).isEqualTo("John Doe Updated");
        verify(clientRepository).save(existing);
    }

    @Test
    @DisplayName("Should throw NotFoundException when updating non-existing client")
    void shouldThrowNotFoundOnUpdate() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(1L, UpdateClientRequest.builder().build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Client not found for update");
    }

    @Test
    @DisplayName("Should Delete Client")
    void shouldDeleteClient() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw NotFoundException when deleting non-existing client")
    void shouldThrowNotFoundOnDelete() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> clientService.delete(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Client not found for deletion");
    }

    @Test
    @DisplayName("Should Find Client by ID")
    void shouldFindById() {
        Client client = FixtureFactory.createClient(1L, "John Doe");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client found = clientService.findById(1L);

        assertThat(found.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should throw NotFoundException when client not found by ID")
    void shouldThrowNotFoundOnFindById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.findById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Client not found");
    }

    @Test
    @DisplayName("Should Find Clients with Filters")
    void shouldFindClientsWithFilters() {
        ClientFilterDTO filter = ClientFilterDTO.builder()
                .name("John Doe")
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Client client = FixtureFactory.createClient(1L, "John Doe");
        Page<Client> page = new PageImpl<>(List.of(client));

        when(clientRepository.findAll((Specification<Client>) any(), eq(pageable))).thenReturn(page);

        Page<Client> result = clientService.findClients(filter, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should Find All Clients")
    void shouldFindAllClients() {
        Pageable pageable = PageRequest.of(0, 10);
        Client client = FixtureFactory.createClient(1L, "John Doe");
        Page<Client> page = new PageImpl<>(List.of(client));

        when(clientRepository.findAll(pageable)).thenReturn(page);

        Page<Client> result = clientService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(clientRepository).findAll(pageable);
    }
}
