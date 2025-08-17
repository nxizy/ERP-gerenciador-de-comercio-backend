package br.com.infoexpert.gerenciador_de_comercio.owner.controller;

import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.OwnerResponse;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.UpdateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.OWNERS;

@RestController
@RequestMapping(OWNERS)
@RequiredArgsConstructor
@Tag(name= "Owner's Management", description = "Operations for creating, updating and retrieving owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Operation(summary = "Create a new owner")
    @PostMapping
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        var owner = ownerService.create(request);
        return ResponseEntity.ok(owner.toOwnerResponse());
    }

    @Operation(summary = "Update an owner")
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long id, @Valid @RequestBody UpdateOwnerRequest request) {
        var owner = ownerService.update(id, request);
        return ResponseEntity.ok(owner.toOwnerResponse());
    }

    @Operation(summary = "Get owner by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> findById(@PathVariable Long id) {
        var owner = ownerService.findById(id);
        return ResponseEntity.ok(owner.toOwnerResponse());
    }
}
