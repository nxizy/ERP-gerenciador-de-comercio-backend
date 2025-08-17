package br.com.infoexpert.gerenciador_de_comercio.product.service;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product create(CreateProductRequest request);

    Product update(Long Id, UpdateProductRequest request);

    void delete(Long Id);

    Page<Product> findAll(Pageable pageable);

    Product findById(Long Id);

    Page<Product> findProducts(ProductFilterDTO productFilterDTO, Pageable pageable);
}