package br.com.infoexpert.gerenciador_de_comercio.owner.service;

import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.UpdateOwnerRequest;

public interface OwnerService {
    Owner create(CreateOwnerRequest request);

    Owner update(Long id, UpdateOwnerRequest request);

    Owner findById(Long id);
}
