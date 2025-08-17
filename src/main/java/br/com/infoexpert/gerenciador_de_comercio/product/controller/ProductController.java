package br.com.infoexpert.gerenciador_de_comercio.product.controller;

import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductResponse;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.PRODUCTS;
import static br.com.infoexpert.gerenciador_de_comercio.utils.ProductMapper.toProductPageResponse;

@RestController
@RequestMapping(PRODUCTS)
@RequiredArgsConstructor
@Tag(name = "Product's Management", description = "Operations for creating, updating and retrieving products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        var product = productService.create(request);
        return ResponseEntity.ok(product.toProductResponse());
    }

    @Operation(summary = "Update an product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        var product = productService.update(id, request);
        return ResponseEntity.ok(product.toProductResponse());
    }

    @Operation(summary = "Delete an product")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get products with optional filters, note that by using stockQuantity param, it will retrieve all products that have the stock quantity given or less.")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(ProductFilterDTO productFilterDTO, Pageable pageable) {
        boolean noFilters = productFilterDTO.isAllNull();

        Page<Product> products;
        if(noFilters) {
            products = productService.findAll(pageable);
        } else {
            products = productService.findProducts(productFilterDTO, pageable);
        }

        return ResponseEntity.ok(toProductPageResponse(products));
    }

    @Operation(summary = "Get product by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        var product = productService.findById(id);
        return ResponseEntity.ok(product.toProductResponse());
    }


}
