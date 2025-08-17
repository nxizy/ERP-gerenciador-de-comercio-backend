package br.com.infoexpert.gerenciador_de_comercio.order.controller;

import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderResponse;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.ORDERS;
import static br.com.infoexpert.gerenciador_de_comercio.utils.OrderMapper.toOrderResponsePage;

@RestController
@RequestMapping(ORDERS)
@RequiredArgsConstructor
@Tag(name= "Order's Management", description = "Operations for creating, updating and retrieving orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        var order = orderService.create(request);
        return ResponseEntity.ok(order.toOrderResponse());
    }

    @Operation(summary = "Update an order")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderRequest request) {
        var order = orderService.update(id, request);
        return ResponseEntity.ok(order.toOrderResponse());
    }

    @Operation(summary = "Delete an order")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get orders with filters")
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrder(@ModelAttribute OrderFilterDTO orderFilterDTO, Pageable pageable) {
        boolean noFilters = orderFilterDTO.isAllNull();

        Page<Order> orders;
        if (noFilters) {
            orders = orderService.findAll(pageable);
        } else {
            orders = orderService.findOrders(orderFilterDTO, pageable);
        }
        return ResponseEntity.ok(toOrderResponsePage(orders));
    }

    @Operation(summary = "Get order by it's id")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        var order = orderService.findById(id);
        return ResponseEntity.ok(order.toOrderResponse());
    }
}
