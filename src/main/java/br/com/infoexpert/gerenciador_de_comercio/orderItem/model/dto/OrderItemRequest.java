package br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull
    private Product product;

    @NotNull
    private Order order;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

}
