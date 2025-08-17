package br.com.infoexpert.gerenciador_de_comercio.order.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto.OrderItemRequest;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto.OrderItemResponse;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderRequest {

    @Valid
    private Client client;

    @Valid
    private Employee employee;

    @Valid
    @NotNull
    private Owner owner;

    @Valid
    private List<OrderItemRequest> items;

    private String clientSolicitation;

    private LocalDateTime entryDate;

    @Valid
    private ServiceStatus serviceStatus;

    private String reminder;

    private LocalDateTime reminderDate;

    private String serviceDescription;

    private BigDecimal servicePrice;

    private LocalDateTime finishedAt;

    private LocalDateTime pickedUpAt;

    private BigDecimal totalAmount;

    private Boolean isPaid;

    @Valid
    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate;

    private Integer printCount;

    private LocalDateTime printDate;

}
