package br.com.infoexpert.gerenciador_de_comercio.product.controller;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.service.ProductService;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/products should create product")
    void shouldCreateProduct() throws Exception {
        CreateProductRequest request = FixtureFactory.createProductRequest();
        Product product = FixtureFactory.createProduct(1L, request.getName());

        when(productService.create(any())).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @DisplayName("PUT /api/products/{id} should update product")
    void shouldUpdateProduct() throws Exception {
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("Produto Atualizado")
                .build();

        Product updatedProduct = FixtureFactory.createProduct(1L, "Produto Atualizado");

        when(productService.update(eq(1L), any())).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Produto Atualizado"));
    }

    @Test
    @DisplayName("DELETE /api/products/{id} should delete product")
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/products with no filters should return all products")
    void shouldReturnAllProducts() throws Exception {
        Product product = FixtureFactory.createProduct(1L, "Produto X");

        when(productService.findAll(any())).thenReturn(new PageImpl<>(List.of(product)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Produto X"));
    }

    @Test
    @DisplayName("GET /api/products with filters should use findProducts")
    void shouldFilterProductsUsingSpecification() throws Exception {
        Product product = FixtureFactory.createProduct(1L, "SSD");
        product.setProductCode("123");
        product.setStockQuantity(5);

        when(productService.findProducts(any(ProductFilterDTO.class), any()))
                .thenReturn(new PageImpl<>(List.of(product)));

        mockMvc.perform(get("/api/products")
                        .param("name", "SSD")
                        .param("productCode", "123")
                        .param("stockQuantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("SSD"))
                .andExpect(jsonPath("$.content[0].productCode").value("123"))
                .andExpect(jsonPath("$.content[0].stockQuantity").value(5));

        ArgumentCaptor<ProductFilterDTO> captor = ArgumentCaptor.forClass(ProductFilterDTO.class);
        verify(productService).findProducts(captor.capture(), any());

        ProductFilterDTO filterUsed = captor.getValue();
        assertEquals("SSD", filterUsed.getName());
        assertEquals("123", filterUsed.getProductCode());
        assertEquals(5, filterUsed.getStockQuantity());
    }

    @Test
    @DisplayName("GET /api/products/{id} should return a single product")
    void shouldReturnProductById() throws Exception {
        Product product = FixtureFactory.createProduct(1L, "Cabo HDMI");

        when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cabo HDMI"));
    }
}
