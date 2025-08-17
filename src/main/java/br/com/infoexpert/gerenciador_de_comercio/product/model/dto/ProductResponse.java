package br.com.infoexpert.gerenciador_de_comercio.product.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product Response Object")
public class ProductResponse {

    @Schema(description = "Product's Id", example = "1L")
    private Long id;

    @Schema(description = "Product's name", example = "Lan Cable")
    private String name;

    @Schema(description = "Product's description", example = "Used to connect devices")
    private String description;

    @Schema(description = "Product's price", example = "15.00")
    private BigDecimal price;

    @Schema(description = "Product's stock quantity", example = "15")
    private Integer stockQuantity;

    @Schema(description = "Product's code", example = "023")
    private String productCode;

    @Schema(description = "Product's NCM", example = "85444900")
    private String NCM;

    @Schema(description = "Product's CFOP", example = "5405")
    private String CFOP;
}
