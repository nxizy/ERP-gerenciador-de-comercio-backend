package br.com.infoexpert.gerenciador_de_comercio.product.service;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should Create Product")
    void shouldCreateProduct() {
        CreateProductRequest request = FixtureFactory.createProductRequest();
        Product savedProduct = FixtureFactory.createProduct(1L, request.getName());

        when(productRepository.existsByProductCode(request.getProductCode())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product response = productService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo(request.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should Update Product")
    void shouldUpdateProduct() {
        Long id = 1L;
        Product existingProduct = FixtureFactory.createProduct(id, "Hard disk 1TB");
        UpdateProductRequest request = FixtureFactory.updateProductRequest();

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product response = productService.update(id, request);

        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
        verify(productRepository).save(existingProduct);
    }

    @Test
    @DisplayName("Should Delete Product")
    void shouldDeleteProduct() {
        Long id = 1L;
        Product product = FixtureFactory.createProduct(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.delete(id);

        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("Should Find Product By ID")
    void shouldFindProductById() {
        Long id = 1L;
        Product product = FixtureFactory.createProduct(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Hard disk 1TB");
        verify(productRepository).findById(id);
    }

    @Test
    @DisplayName("Should Return All Products")
    void shouldFindAllProducts() {
        Pageable pageable = Pageable.ofSize(10);
        var page = FixtureFactory.createProductPage();

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.findAll(pageable);

        assertThat(result.getContent()).hasSize(3);
        verify(productRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should Throw Exception When Product Not Found")
    void shouldThrowWhenProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found");
    }

    @Test
    @DisplayName("Should Find Products Using Filters")
    void shouldFilterProducts() {
        Product product = FixtureFactory.createProduct(1L, "SSD 512GB");
        ProductFilterDTO filter = ProductFilterDTO.builder()
                .name("SSD 512GB")
                .productCode("123456789")
                .stockQuantity(5)
                .build();

        when(productRepository.findAll((Specification<Product>) any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(product)));

        Page<Product> result = productService.findProducts(filter, Pageable.ofSize(10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("SSD 512GB");
        verify(productRepository).findAll((Specification<Product>) any(), any(Pageable.class));
    }
}
