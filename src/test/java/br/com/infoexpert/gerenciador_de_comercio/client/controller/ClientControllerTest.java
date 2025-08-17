package br.com.infoexpert.gerenciador_de_comercio.client.controller;

import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.service.ClientService;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("POST /api/clients should create client")
    void shouldCreateClient() throws Exception {
        CreateClientRequest request = FixtureFactory.createClientRequest();
        Client client = FixtureFactory.createClient(1L);

        when(clientService.create(any())).thenReturn(client);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("PUT /api/clients/{id} should update client")
    void shouldUpdateClient() throws Exception {
        UpdateClientRequest request = UpdateClientRequest.builder()
                .name("John updated")
                .build();

        Client client = FixtureFactory.createClient(1L);
        client.setName("John updated");

        when(clientService.update(eq(1L), any())).thenReturn(client);

        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John updated"));
    }

    @Test
    @DisplayName("DELETE /api/clients/{id} should delete client")
    void shouldDeleteClient() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/clients should return clients filtered")
    void shouldReturnClientsFiltered() throws Exception {
        Client client = FixtureFactory.createClient(1L);

        when(clientService.findClients(any(), any()))
                .thenReturn(new PageImpl<>(List.of(client)));

        mockMvc.perform(get("/api/clients")
                        .param("name", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John Doe"));
    }

    @Test
    @DisplayName("GET /api/clients should return all clients if no filter is given")
    void shouldReturnAllClientsIfNoFilter() throws Exception {
        Client client = FixtureFactory.createClient(1L);

        when(clientService.findAll(any()))
                .thenReturn(new PageImpl<>(List.of(client)));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}
