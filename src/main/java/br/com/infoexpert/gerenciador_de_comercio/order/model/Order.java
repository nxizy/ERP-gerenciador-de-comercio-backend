package br.com.infoexpert.gerenciador_de_comercio.order.model;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderResponse;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String clientSolicitation;

    private LocalDateTime entryDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ServiceStatus serviceStatus = ServiceStatus.OPEN;

    private String reminder;

    private LocalDateTime reminderDate;

    private String serviceDescription;

    @Column(precision = 10, scale = 2)
    private BigDecimal servicePrice;

    private LocalDateTime finishedAt;

    private LocalDateTime pickedUpAt;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Builder.Default
    private Boolean isPaid = false;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate;

    private Integer printCount;

    private LocalDateTime printDate;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void validateOrder() {
        if (Boolean.TRUE.equals(isPaid)) {
            if (paymentDate == null || paymentMethod == null) {
                throw new IllegalStateException("Pedidos pagos devem conter data e forma de pagamento.");
            }
        }
    }

    public void merge(UpdateOrderRequest request) {
        if(request.getClient() != null) this.client = request.getClient();
        if(request.getEmployee() != null) this.employee = request.getEmployee();
        if(request.getOwner() != null) this.owner = request.getOwner();
//      if(request.getItems() != null) this.items = request.getItems();
        if(request.getClientSolicitation() != null) this.clientSolicitation = request.getClientSolicitation();
        if(request.getEntryDate() != null) this.entryDate = request.getEntryDate();
        if(request.getServiceStatus() != null) this.serviceStatus = request.getServiceStatus();
        if(request.getReminder() != null) this.reminder = request.getReminder();
        if(request.getReminderDate() != null) this.reminderDate = request.getReminderDate();
        if(request.getServiceDescription() != null) this.serviceDescription = request.getServiceDescription();
        if(request.getServicePrice() != null) this.servicePrice = request.getServicePrice();
        if(request.getFinishedAt() != null) this.finishedAt = request.getFinishedAt();
        if(request.getPickedUpAt() != null) this.pickedUpAt = request.getPickedUpAt();
        if(request.getTotalAmount() != null) this.totalAmount = request.getTotalAmount();
        if(request.getIsPaid() != null) this.isPaid = request.getIsPaid();
        if(request.getPaymentMethod() != null) this.paymentMethod = request.getPaymentMethod();
        if(request.getPaymentDate() != null) this.paymentDate = request.getPaymentDate();
        if(request.getPrintCount() != null) this.printCount = request.getPrintCount();
        if(request.getPrintDate() != null) this.printDate = request.getPrintDate();
    }


    //toOrderResponse
    public OrderResponse toOrderResponse() {
        return OrderResponse.builder()
                .id(this.id)
                .client(this.client.toClientResponse())
                .employee(this.employee.toEmployeeResponse())
                .owner(this.owner.toOwnerResponse())
//               .items(this.items)
                .clientSolicitation(this.clientSolicitation)
                .entryDate(this.entryDate)
                .serviceStatus(this.serviceStatus)
                .reminder(this.reminder)
                .reminderDate(this.reminderDate)
                .serviceDescription(this.serviceDescription)
                .servicePrice(this.servicePrice)
                .finishedAt(this.finishedAt)
                .pickedUpAt(this.pickedUpAt)
                .totalAmount(this.totalAmount)
                .isPaid(this.isPaid)
                .paymentMethod(this.paymentMethod)
                .paymentDate(this.paymentDate)
                .printCount(this.printCount)
                .printDate(this.printDate)
                .build();
    }

}
