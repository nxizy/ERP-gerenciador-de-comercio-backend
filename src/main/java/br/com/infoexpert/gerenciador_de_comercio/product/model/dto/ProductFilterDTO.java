package br.com.infoexpert.gerenciador_de_comercio.product.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ProductFilterDTO {
    private String name;
    private String productCode;
    private Integer stockQuantity;

    public boolean isAllNull() {
        return name == null &&
                productCode == null &&
                stockQuantity == null;
    }
}
