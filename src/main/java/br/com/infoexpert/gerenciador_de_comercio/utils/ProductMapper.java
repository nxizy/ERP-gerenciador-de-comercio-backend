package br.com.infoexpert.gerenciador_de_comercio.utils;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductResponse;
import org.springframework.data.domain.Page;

public class ProductMapper {

    public static Page<ProductResponse> toProductPageResponse(Page<Product> products) {
        return products.map(Product::toProductResponse);
    }
}
