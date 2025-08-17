package br.com.infoexpert.gerenciador_de_comercio.order.model.dto;

import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderFilterDTO {
    private String clientName;
    private Long clientId;
    private ServiceStatus serviceStatus;
    private LocalDateTime creationStartDate;
    private LocalDateTime creationEndDate;
    private LocalDateTime entryStartDate;
    private LocalDateTime entryEndDate;
    private Long employeeId;
    private LocalDateTime reminderDate;
    private Boolean paymentSituation;
    private PaymentMethod paymentMethod;
    private Long productId;

    public boolean isAllNull() {
        return clientId == null &&
                clientName == null &&
                serviceStatus == null &&
                creationStartDate == null &&
                creationEndDate == null &&
                entryStartDate == null &&
                entryEndDate == null &&
                employeeId == null &&
                reminderDate == null &&
                paymentSituation == null &&
                paymentMethod == null &&
                productId == null;
    }
}
