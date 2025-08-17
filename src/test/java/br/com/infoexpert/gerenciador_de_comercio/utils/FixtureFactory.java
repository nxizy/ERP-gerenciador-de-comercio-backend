package br.com.infoexpert.gerenciador_de_comercio.utils;

import br.com.infoexpert.gerenciador_de_comercio.address.model.Address;
import br.com.infoexpert.gerenciador_de_comercio.address.model.dto.AddressDTO;
import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.model.dto.CreateClientRequest;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.CreateEmployeeRequest;
import br.com.infoexpert.gerenciador_de_comercio.enums.ClientType;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.enums.UF;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.dto.OrderItemRequest;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.dto.CreateOwnerRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.CreateProductRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FixtureFactory {

    public static AddressDTO createAddress() {
        return AddressDTO.builder()
                .address("Rua X, 0")
                .district("Centro")
                .city("Campinas")
                .uf(UF.SP)
                .CEP("13330-200")
                .build();
    }

    public static Client createClient(Long id, String name) {
        return Client.builder()
                .id(id)
                .name(name)
                .type(ClientType.PHYSICAL)
                .address(createAddress())
                .document("299.552.588-02")
                .contactName("John")
                .phoneNumber("(19)99999-9999")
                .build();
    }

    public static Client createClient(Long id) {
        return Client.builder()
                .id(id)
                .name("John Doe")
                .type(ClientType.PHYSICAL)
                .address(createAddress())
                .document("299.552.588-02")
                .contactName("John")
                .phoneNumber("(19)99999-9999")
                .build();
    }

    public static Client createClient() {
        return Client.builder()
                .name("John Doe")
                .type(ClientType.PHYSICAL)
                .address(createAddress())
                .document("299.552.588-02")
                .contactName("John")
                .phoneNumber("(19)99999-9999")
                .build();
    }


    public static CreateClientRequest createClientRequest() {
        return CreateClientRequest.builder()
                .name("John Doe")
                .type(ClientType.PHYSICAL)
                .address(createAddress())
                .document("299.552.588-02")
                .contactName("John")
                .phoneNumber("(19)99999-9999")
                .build();
    }

    public static Owner createOwner(Long id, String name, String password) {
        return Owner.builder()
                .id(id)
                .name(name)
                .password(password)
                .address(createAddress())
                .document("97.055.369/0001-33")
                .phoneNumber("(19)98888-9999")
                .build();
    }

    public static Owner createOwner(Long id) {
        return Owner.builder()
                .id(id)
                .name("Empresa Y")
                .password("123456")
                .address(createAddress())
                .document("97.055.369/0001-33")
                .phoneNumber("(19)98888-9999")
                .build();
    }

    public static Owner createOwner() {
        return Owner.builder()
                .name("Empresa Y")
                .password("123456")
                .address(createAddress())
                .document("97.055.369/0001-33")
                .phoneNumber("(19)98888-9999")
                .build();
    }

    public static CreateOwnerRequest createOwnerRequest() {
        return CreateOwnerRequest.builder()
                .name("Empresa Y")
                .password("123456")
                .address(createAddress())
                .document("97.055.369/0001-33")
                .phoneNumber("(19)98888-9999")
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .name("Hard disk 1TB")
                .description("Hard disk 1TB 50MB/S Kingston")
                .price(BigDecimal.valueOf(150.00))
                .stockQuantity(10)
                .productCode("123456789")
                .NCM("84717012")
                .CFOP("5245")
                .build();
    }

    public static Product createProduct(Long id) {
        return Product.builder()
                .id(id)
                .name("Hard disk 1TB")
                .description("Hard disk 1TB 50MB/S Kingston")
                .price(new BigDecimal("150.00"))
                .stockQuantity(10)
                .productCode("123456789")
                .NCM("84717012")
                .CFOP("5245")
                .build();
    }

    public static Product createProduct(Long id, String name) {
        return Product.builder()
                .id(id)
                .name(name)
                .description("Hard disk 1TB 50MB/S Kingston")
                .price(new BigDecimal("150.00"))
                .stockQuantity(10)
                .productCode("123456789")
                .NCM("84717012")
                .CFOP("5245")
                .build();
    }

    public static Page<Product> createProductPage() {
        List<Product> products = List.of(
                createProduct(1L, "Produto 1"),
                createProduct(2L, "Produto 2"),
                createProduct(3L, "Produto 3")
        );
        return new PageImpl<>(products, Pageable.ofSize(10), products.size());
    }

    public static CreateProductRequest createProductRequest() {
        return CreateProductRequest.builder()
                .name("HD Externo")
                .description("1TB USB 3.0")
                .price(new BigDecimal("199.90"))
                .stockQuantity(20)
                .productCode("ABC123")
                .NCM("84717012")
                .CFOP("5102")
                .build();
    }

    public static UpdateProductRequest updateProductRequest() {
        return UpdateProductRequest.builder()
                .name("Hard disk 500GB")
                .description("Hard disk 500GB 50MB/S Kingston")
                .price(new BigDecimal("150.00"))
                .stockQuantity(10)
                .productCode("123456789")
                .NCM("84717012")
                .CFOP("5245")
                .build();
    }


    public static Employee createEmployee(Long id, String name) {
        return Employee.builder()
                .id(id)
                .name(name)
                .document("401.662.290-59")
                .function("Technician")
                .build();
    }


    public static Employee createEmployee(Long id) {
        return Employee.builder()
                .id(id)
                .name("Peter")
                .document("401.662.290-59")
                .function("Technician")
                .build();
    }

    public static Employee createEmployee() {
        return Employee.builder()
                .name("Peter")
                .document("401.662.290-59")
                .function("Technician")
                .build();
    }

    public static CreateEmployeeRequest createEmployeeRequest() {
        return CreateEmployeeRequest.builder()
                .name("Peter")
                .document("401.662.290-59")
                .function("Technician")
                .build();
    }

    public static CreateOrderRequest createOrderRequest(Client client, Employee employee, Owner owner) {
        return CreateOrderRequest.builder()
                .clientId(client.getId())
                .employeeId(employee.getId())
                .ownerId(owner.getId())
                .items(createOrderItemRequestList())
                .createdAt(LocalDateTime.parse("2025-08-04T11:30:00"))
                .clientSolicitation("Notebook does not charge")
                .entryDate(LocalDateTime.parse("2025-08-04T11:31:00"))
                .serviceStatus(ServiceStatus.CLOSED)
                .reminder("Notify when battery arrives")
                .reminderDate(LocalDateTime.parse("2025-08-06T13:00:00"))
                .serviceDescription("Battery replaced, power supply ok")
                .servicePrice(BigDecimal.valueOf(80.00))
                .finishedAt(LocalDateTime.parse("2025-08-06T16:00:00"))
                .pickedUpAt(LocalDateTime.parse("2025-08-07T09:00:00"))
                .totalAmount(BigDecimal.valueOf(200.00))
                .isPaid(true)
                .paymentMethod(PaymentMethod.CARD)
                .paymentDate(LocalDateTime.parse("2025-08-07T09:00:00"))
                .printCount(2)
                .printDate(LocalDateTime.parse("2025-08-04T11:31:40"))
                .build();
    }

    public static UpdateOrderRequest updateOrderRequest() {
        return UpdateOrderRequest.builder()
                .items(createOrderItemRequestList())
                .clientSolicitation("Battery replacement")
                .serviceStatus(ServiceStatus.CLOSED)
                .reminder("Confirm delivery")
                .reminderDate(LocalDateTime.parse("2025-08-08T15:00:00"))
                .serviceDescription("Battery replaced, tested successfully")
                .servicePrice(BigDecimal.valueOf(120.00))
                .totalAmount(BigDecimal.valueOf(200.00))
                .isPaid(true)
                .paymentMethod(PaymentMethod.CASH)
                .paymentDate(LocalDateTime.parse("2025-08-08T16:00:00"))
                .printCount(1)
                .printDate(LocalDateTime.parse("2025-08-08T16:10:00"))
                .build();
    }

    public static Order createOrder(Client client, Employee employee, Owner owner) {
        return Order.builder()
                .client(client)
                .employee(employee)
                .owner(owner)
                .items(createOrderItemList())
                .createdAt(LocalDateTime.parse("2025-08-04T11:30:00"))
                .clientSolicitation("Notebook does not charge")
                .entryDate(LocalDateTime.parse("2025-08-04T11:31:00"))
                .serviceStatus(ServiceStatus.CLOSED)
                .reminder("Notify when battery arrives")
                .reminderDate(LocalDateTime.parse("2025-08-06T13:00:00"))
                .serviceDescription("Battery replaced, power supply ok")
                .servicePrice(BigDecimal.valueOf(80.00))
                .finishedAt(LocalDateTime.parse("2025-08-06T16:00:00"))
                .pickedUpAt(LocalDateTime.parse("2025-08-07T09:00:00"))
                .totalAmount(BigDecimal.valueOf(200.00))
                .isPaid(true)
                .paymentMethod(PaymentMethod.CARD)
                .paymentDate(LocalDateTime.parse("2025-08-07T09:00:00"))
                .printCount(2)
                .printDate(LocalDateTime.parse("2025-08-04T11:31:40"))
                .build();
    }

    public static Order createOrder(Long id, Client client, Employee employee, Owner owner) {
        return Order.builder()
                .id(id)
                .client(client)
                .employee(employee)
                .owner(owner)
                .items(createOrderItemList())
                .createdAt(LocalDateTime.parse("2025-08-04T11:30:00"))
                .clientSolicitation("Notebook does not charge")
                .entryDate(LocalDateTime.parse("2025-08-04T11:31:00"))
                .serviceStatus(ServiceStatus.CLOSED)
                .reminder("Notify when battery arrives")
                .reminderDate(LocalDateTime.parse("2025-08-06T13:00:00"))
                .serviceDescription("Battery replaced, power supply ok")
                .servicePrice(BigDecimal.valueOf(80.00))
                .finishedAt(LocalDateTime.parse("2025-08-06T16:00:00"))
                .pickedUpAt(LocalDateTime.parse("2025-08-07T09:00:00"))
                .totalAmount(BigDecimal.valueOf(200.00))
                .isPaid(true)
                .paymentMethod(PaymentMethod.CARD)
                .paymentDate(LocalDateTime.parse("2025-08-07T09:00:00"))
                .printCount(2)
                .printDate(LocalDateTime.parse("2025-08-04T11:31:40"))
                .build();
    }

    public static Order createOrder(Long id, Client client, Employee employee, Owner owner, String clientSolicitation, String serviceDescription) {
        return Order.builder()
                .id(id)
                .client(client)
                .employee(employee)
                .owner(owner)
                .items(createOrderItemList())
                .createdAt(LocalDateTime.parse("2025-08-04T11:30:00"))
                .clientSolicitation(clientSolicitation)
                .entryDate(LocalDateTime.parse("2025-08-04T11:31:00"))
                .serviceStatus(ServiceStatus.CLOSED)
                .reminder("Notify when battery arrives")
                .reminderDate(LocalDateTime.parse("2025-08-06T13:00:00"))
                .serviceDescription(serviceDescription)
                .servicePrice(BigDecimal.valueOf(80.00))
                .finishedAt(LocalDateTime.parse("2025-08-06T16:00:00"))
                .pickedUpAt(LocalDateTime.parse("2025-08-07T09:00:00"))
                .totalAmount(BigDecimal.valueOf(200.00))
                .isPaid(true)
                .paymentMethod(PaymentMethod.CARD)
                .paymentDate(LocalDateTime.parse("2025-08-07T09:00:00"))
                .printCount(2)
                .printDate(LocalDateTime.parse("2025-08-04T11:31:40"))
                .build();
    }

    public static Page<Order> createOrderPage(Client client, Employee employee, Owner owner) {
        List<Order> orders = List.of(
                createOrder(1L, client, employee, owner, "CPU does not turn on", "Power Supply replaced"),
                createOrder(2L, client, employee, owner, "Notebook with multiple dots on screen", "Screen replaced"),
                createOrder(3L, client, employee, owner, "HD not working", "HD not accessible, nothing to do")
        );
        return new PageImpl<>(orders, Pageable.ofSize(10), orders.size());
    }


    public static List<OrderItem> createOrderItemList() {
        Product product1 = createProduct(1L, "Produto 1");
        Product product2 = createProduct(2L, "Produto 2");

        return List.of(
                OrderItem.builder()
                        .product(product1)
                        .quantity(2)
                        .unitPrice(new BigDecimal("10.00"))
                        .build(),
                OrderItem.builder()
                        .product(product2)
                        .quantity(3)
                        .unitPrice(new BigDecimal("20.00"))
                        .build()
        );
    }

    public static OrderItem createOrderItem(Product product, Integer quantity, BigDecimal unitPrice) {
        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
    }

    public static OrderItemRequest createOrderItemRequest(Product product, Integer quantity, BigDecimal unitPrice) {
        return OrderItemRequest.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
    }


    public static List<OrderItemRequest> createOrderItemRequestList() {
        Product product1 = createProduct(1L, "Produto 1");
        Product product2 = createProduct(2L, "Produto 2");

        return List.of(
                createOrderItemRequest(product1, 2, new BigDecimal("10.00")),
                createOrderItemRequest(product2, 3, new BigDecimal("20.00"))
        );
    }

}
