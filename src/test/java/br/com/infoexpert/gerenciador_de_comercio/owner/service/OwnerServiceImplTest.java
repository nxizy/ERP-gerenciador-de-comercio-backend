package br.com.infoexpert.gerenciador_de_comercio.owner.service;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.repository.OwnerRepository;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create Owner")
    void shouldCreateOwner() {
        //given
        CreateOwnerRequest request = CreateOwnerRequest.builder()
                .name("Empresa Y")
                .password("123456")
                .address(FixtureFactory.createAddress())
                .document("97.055.369/0001-33")
                .phoneNumber("(19)98888-9999")
                .build();

        Owner savedOwner = FixtureFactory.createOwner(1L, "Empresa Y", "123456");

        when(ownerRepository.save(any(Owner.class))).thenReturn(savedOwner);

        //when

        Owner response = ownerService.create(request);

        //then

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Empresa Y");
        assertThat(response.getPassword()).isEqualTo("123456");


        verify(ownerRepository).save(any(Owner.class));

    }

    @Test
    @DisplayName("Should find owner by it's id")
    void shouldFindOwnerById() {
        //given
        Owner savedOwner = FixtureFactory.createOwner(1L, "Empresa Y", "123456");

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(savedOwner));

        //when
        Owner result = ownerService.findById(1L);

        //then

        assertThat(result.getName()).isEqualTo("Empresa Y");
        verify(ownerRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw when owner is not found by Id")
    void shouldThrowIfOwnerNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.findById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Owner not found");
    }


}
