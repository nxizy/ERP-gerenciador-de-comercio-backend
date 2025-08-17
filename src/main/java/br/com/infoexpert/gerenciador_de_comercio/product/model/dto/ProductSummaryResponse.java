package br.com.infoexpert.gerenciador_de_comercio.product.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSummaryResponse {

    private Long id;

    private String name;
}
