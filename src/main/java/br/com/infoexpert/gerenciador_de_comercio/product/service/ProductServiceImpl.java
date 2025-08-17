package br.com.infoexpert.gerenciador_de_comercio.product.service;

import br.com.infoexpert.gerenciador_de_comercio.exceptions.ConflictException;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(CreateProductRequest request) {
        if(productRepository.existsByProductCode(request.getProductCode())) {
            throw new ConflictException("Product already registered");
        }
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .productCode(request.getProductCode())
                .NCM(request.getNCM())
                .CFOP(request.getCFOP())
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product update(Long Id, UpdateProductRequest request) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Product not found for update"));

        product.merge(request);
        return productRepository.save(product);
    }

    @Override
    public void delete(Long Id) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Product not found for delete"));

        productRepository.delete(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long Id) {
        return productRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

    }

    @Override
    public Page<Product> findProducts(ProductFilterDTO filter, Pageable pageable) {
        Specification<Product> spec = (root, query, cb) -> null;

        if(filter.getName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("name"), filter.getName()));
        }

        if(filter.getProductCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("productCode"), filter.getProductCode()));
        }

        if(filter.getStockQuantity() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stockQuantity"), filter.getStockQuantity()));
        }

        return productRepository.findAll(spec, pageable);
    }
}
