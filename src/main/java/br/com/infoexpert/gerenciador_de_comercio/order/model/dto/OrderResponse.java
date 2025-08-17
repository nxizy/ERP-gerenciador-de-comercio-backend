package br.com.infoexpert.gerenciador_de_comercio.order.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.ClientResponse;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeResponse;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto.OrderItemResponse;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.OwnerResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Order Response Object")
public class OrderResponse {

    @Schema(description = "Order's Id", example = "1L")
    private Long id;

    @Schema(
            description = "Employee Response Object",
            example = "{\n" +
                    "  \"id\": 1,\n" +
                    "  \"name\": \"Neymar\",\n" +
                    "  \"document\": \"111.111.111-11\",\n" +
                    "  \"function\": \"MotherBoard Technician\"\n" +
                    "}"
    )
    private EmployeeResponse employee;

    @Schema(
            description = "Owner Response Object",
            example = "{\n" +
                    "  \"id\": 1,\n" +
                    "  \"name\": \"InfoExpert\",\n" +
                    "  \"address\": {\n" +
                    "    \"address\": \"Rua das Flores, 123\",\n" +
                    "    \"district\": \"Centro\",\n" +
                    "    \"city\": \"São Paulo\",\n" +
                    "    \"uf\": \"SP\",\n" +
                    "    \"CEP\": \"01234-567\"\n" +
                    "  },\n" +
                    "  \"document\": \"11.111.111/0001-11\",\n" +
                    "  \"phoneNumber\": \"(11) 99999-9999\",\n" +
                    "  \"phoneNumber2\": \"(11) 99999-9999\"\n" +
                    "}"
    )
    private OwnerResponse owner;

    @Schema(
            description = "Client Response Object",
            example = "{\n" +
                    "  \"id\": 1,\n" +
                    "  \"name\": \"John Doe\",\n" +
                    "  \"type\": \"LEGAL\",\n" +
                    "  \"address\": {\n" +
                    "    \"address\": \"Rua das Flores, 123\",\n" +
                    "    \"district\": \"Centro\",\n" +
                    "    \"city\": \"São Paulo\",\n" +
                    "    \"uf\": \"SP\",\n" +
                    "    \"CEP\": \"01234-567\"\n" +
                    "  },\n" +
                    "  \"document\": \"111.111.111-11\",\n" +
                    "  \"stateRegistration\": \"111.111.111.111\",\n" +
                    "  \"contactName\": \"John\",\n" +
                    "  \"phoneNumber\": \"(11) 99999-9999\",\n" +
                    "  \"phoneNumber2\": \"(11) 88888-8888\"\n" +
                    "}"
    )
    private ClientResponse client;


    @Schema(
            description = "List of each item of the Order",
            implementation = OrderItemResponse.class,
            example = "[" +
                    "{" +
                    "\"product\":" +
                    "{\"id\":1,\"name\":\"Lan Cable\"}," +
                    "\"unitPrice\":15.99,\"quantity\":4,\"totalPrice\":63.96" +
                    "}" +
                    "]"
    )
    private List<OrderItemResponse> orderItems;

    @Schema(description = "Date and time when the Order was created",
            type = "string",
            format = "date-time",
            example = "2025-08-01T11:34:50"
    )
    private LocalDateTime createdAt;

    @Schema(description = "Client's solicitation", example = "Notebook does not charge")
    private String clientSolicitation;

    @Schema(description = "Date and time when the device or service was accepted",
            type = "string",
            format = "date-time",
            example = "2025-08-01T11:35:50"
    )
    private LocalDateTime entryDate;

    @Schema(description = "Order's Sevice Status", example = "IN_PROGRESS")
    private ServiceStatus serviceStatus;

    @Schema(description = "Order's Reminder description", example = "Remember to notify that the notebook is ready to pickup")
    private String reminder;

    @Schema(description = "Date and time when the reminder is set to", example = "2025-08-03T16:24:00")
    private LocalDateTime reminderDate;

    @Schema(description = "Order's Service Description", example = "Changed the battery of the device")
    private String serviceDescription;

    @Schema(description = "Order's Service Price", type = "number", example = "150.00")
    private BigDecimal servicePrice;

    @Schema(description = "Date and time that the service was finished", example = "2025-08-02T11:10:24")
    private LocalDateTime finishedAt;

    @Schema(description = "Date and time that the client picked up the device", example = "2025-08-04T16:12:48")
    private LocalDateTime pickedUpAt;

    @Schema(description = "Order's total amount", example = "750.00")
    private BigDecimal totalAmount;

    @Schema(description = "Register whether the payment was made or not", example = "FALSE")
    private Boolean isPaid;

    @Schema(description = "Payment method", example = "CARD")
    private PaymentMethod paymentMethod;

    @Schema(description = "Payment date", example = "2025-08-04T16:12:48")
    private LocalDateTime paymentDate;

    @Schema(description = "Amount of times that the order was printed", example = "3")
    private Integer printCount;

    @Schema(description = "Date and time of the last time the order was printed", example = "2025-08-02T12:00:12")
    private LocalDateTime printDate;

}
