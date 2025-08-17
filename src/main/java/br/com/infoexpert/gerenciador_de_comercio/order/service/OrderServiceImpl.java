package br.com.infoexpert.gerenciador_de_comercio.order.service;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.repository.ClientRepository;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.repository.EmployeeRepository;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.exceptions.NotFoundException;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.repository.OrderRepository;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.repository.OwnerRepository;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final OwnerRepository ownerRepository;

    private final EmployeeRepository employeeRepository;

    private final ProductRepository productRepository;

    @Override
    public Order create(CreateOrderRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Employee employee = null;
        if (request.getEmployeeId() != null) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
        }

        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        List<OrderItem> itemsList = request.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProduct().getId())
                        .orElseThrow(() -> new NotFoundException("Product not found"));
                return OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getUnitPrice())
                    .totalPrice(itemRequest.getTotalPrice())
                    .build();
                }).toList();

        Order order = Order.builder()
                .client(client)
                .employee(employee)
                .owner(owner)
                .items(itemsList)
                .createdAt(request.getCreatedAt())
                .clientSolicitation(request.getClientSolicitation())
                .entryDate(request.getEntryDate())
                .serviceStatus(request.getServiceStatus())
                .reminder(request.getReminder())
                .reminderDate(request.getReminderDate())
                .serviceDescription(request.getServiceDescription())
                .servicePrice(request.getServicePrice())
                .finishedAt(request.getFinishedAt())
                .pickedUpAt(request.getPickedUpAt())
                .totalAmount(request.getTotalAmount())
                .isPaid(request.getIsPaid())
                .paymentMethod(request.getPaymentMethod())
                .paymentDate(request.getPaymentDate())
                .printCount(request.getPrintCount())
                .printDate(request.getPrintDate())
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if(order.getServiceStatus() == ServiceStatus.CLOSED) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                if (product.getStockQuantity() < item.getQuantity()) {
                    throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
                }

                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productRepository.save(product);
            }
        }

        if(order.getServiceStatus() == ServiceStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }


        order.merge(request);
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public Page<Order> findOrders(OrderFilterDTO filter, Pageable pageable) {
        Specification<Order> spec = (root, query, cb) -> null;

        if (filter.getClientId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("client").get("id"), filter.getClientId()));
        }

        if (filter.getClientName() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("client").get("name")), "%" + filter.getClientName().toLowerCase() + "%"));
        }

        if (filter.getServiceStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("serviceStatus"), filter.getServiceStatus()));
        }

        if (filter.getCreationStartDate() != null && filter.getCreationEndDate() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("createdAt"), filter.getCreationStartDate(), filter.getCreationEndDate()));
        }

        if (filter.getEntryStartDate() != null && filter.getEntryEndDate() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("entryDate"), filter.getEntryStartDate(), filter.getEntryEndDate()));
        }

        if (filter.getEmployeeId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("employee").get("id"), filter.getEmployeeId()));
        }

        if (filter.getReminderDate() != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("reminderDate"), filter.getReminderDate()));
        }

        if (filter.getPaymentSituation() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isPaid"), filter.getPaymentSituation()));
        }

        if (filter.getPaymentMethod() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("paymentMethod"), filter.getPaymentMethod()));
        }

        return orderRepository.findAll(spec, pageable);
    }

}
