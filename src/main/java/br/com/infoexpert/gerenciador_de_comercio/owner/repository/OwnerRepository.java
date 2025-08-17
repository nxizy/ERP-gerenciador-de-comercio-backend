package br.com.infoexpert.gerenciador_de_comercio.owner.repository;

import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
