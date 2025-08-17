package br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductSummaryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Order Item Response Object")
public class OrderItemResponse {

    @Schema(
            description = "Product Response Object",
            example = "{\n" +
                    "   \"id\": 1, \n" +
                    "   \"name\": Lan Cable" +
                    "}"
    )
    private ProductSummaryResponse product;

    @Schema(description = "Item's price", example = "15.99")
    private BigDecimal unitPrice;

    @Schema(description = "Item's quantity", example = "4")
    private Integer quantity;

    @Schema(description = "Item's total price", example = "50.00")
    private BigDecimal totalPrice;

}
