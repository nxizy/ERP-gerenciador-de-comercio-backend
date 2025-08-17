package br.com.infoexpert.gerenciador_de_comercio.client.service;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.UpdateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.client.repository.ClientRepository;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.ConflictException;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public Client create(CreateClientRequest request) {
        if(clientRepository.existsByDocument(request.getDocument())) {
            throw new ConflictException("Document already registered");
        }
        Client client = Client.builder()
                .name(request.getName())
                .type(request.getType())
                .address(request.getAddress())
                .document(Client.sanitize(request.getDocument()))
                .stateRegistration(Client.sanitize(request.getStateRegistration()))
                .contactName(request.getContactName())
                .phoneNumber(Client.sanitize(request.getPhoneNumber()))
                .phoneNumber2(Client.sanitize(request.getPhoneNumber2()))
                .build();

        client.validateDocument();

        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, UpdateClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found for update"));

        client.merge(request);
        client.validateDocument();
        return clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new NotFoundException("Client not found for deletion");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    @Override
    public Page<Client> findClients(ClientFilterDTO filter, Pageable pageable) {
        //TODO: Change to Specification.unrestricted() when its released.
        Specification<Client> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("name"), filter.getName()));
        }

        if (filter.getDocument() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("document"), filter.getDocument()));
        }

        if(filter.getPhoneNumber() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
        }

        return clientRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

}
