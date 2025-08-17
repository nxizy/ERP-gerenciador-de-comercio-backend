package br.com.infoexpert.gerenciador_de_comercio.owner.controller;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.UpdateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.service.OwnerService;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/owners should create client")
    void shouldCreateClient() throws Exception {
        CreateOwnerRequest request = FixtureFactory.createOwnerRequest();

        Owner owner = FixtureFactory.createOwner(1L);

        when(ownerService.create(any())).thenReturn(owner);

        mockMvc.perform(post("/api/owners")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @DisplayName("PUT /api/owners/{id} should update client")
    void shouldUpdateOwner() throws Exception {
        UpdateOwnerRequest request = UpdateOwnerRequest.builder()
                .name("Info Expert Updated")
                .build();

        Owner owner = FixtureFactory.createOwner(1L);
        owner.setName("Info Expert Updated");

        when(ownerService.update(eq(1L), any())).thenReturn(owner);

        mockMvc.perform(put("/api/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Info Expert Updated"));
    }


    @Test
    @DisplayName("GET /api/owners/{id}")
    void shouldReturnOwnerById() throws Exception {
        Owner owner = FixtureFactory.createOwner(1L);
        when(ownerService.findById(1L)).thenReturn(owner);

        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Empresa Y"))
                .andExpect(jsonPath("$.document").value("97.055.369/0001-33"));

    }
}

