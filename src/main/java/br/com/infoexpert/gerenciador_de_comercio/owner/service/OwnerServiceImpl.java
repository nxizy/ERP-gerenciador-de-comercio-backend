package br.com.infoexpert.gerenciador_de_comercio.owner.service;

import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.UpdateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Owner create(CreateOwnerRequest request) {
        Owner owner = Owner.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .document(request.getDocument())
                .phoneNumber(request.getPhoneNumber())
                .phoneNumber2(request.getPhoneNumber2())
                .build();
        owner.validateDocument();
        return ownerRepository.save(owner);
    }


    @Override
    public Owner update(Long id, UpdateOwnerRequest request) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner not found for update"));

        owner.merge(request);
        owner.validateDocument();
        return ownerRepository.save(owner);
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner not found"));
    }

}
