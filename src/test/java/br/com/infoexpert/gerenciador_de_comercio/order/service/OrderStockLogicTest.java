package br.com.infoexpert.gerenciador_de_comercio.order.service;

import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderStockLogicTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private br.com.infoexpert.gerenciador_de_comercio.order.repository.OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderItem item1;
    private Product product1;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = Product.builder()
                .id(1L)
                .name("Produto X")
                .stockQuantity(10)
                .price(BigDecimal.valueOf(50))
                .build();

        item1 = OrderItem.builder()
                .product(product1)
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(50))
                .totalPrice(BigDecimal.valueOf(150))
                .build();

        order = Order.builder()
                .id(1L)
                .items(List.of(item1))
                .serviceStatus(ServiceStatus.OPEN)
                .build();
    }

    @Test
    @DisplayName("Should decrease stock when closing order")
    void shouldDecreaseStockOnClose() {
        order.setServiceStatus(ServiceStatus.CLOSED);
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setServiceStatus(ServiceStatus.CLOSED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        orderService.update(1L, request);

        assertThat(product1.getStockQuantity()).isEqualTo(7);
        verify(productRepository).save(product1);
    }

    @Test
    @DisplayName("Should increase stock when cancelling order")
    void shouldIncreaseStockOnCancel() {
        order.setServiceStatus(ServiceStatus.CANCELLED);
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setServiceStatus(ServiceStatus.CANCELLED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        orderService.update(1L, request);

        assertThat(product1.getStockQuantity()).isEqualTo(13);
        verify(productRepository).save(product1);
    }

    @Test
    @DisplayName("Should throw exception if stock is insufficient")
    void shouldThrowWhenStockInsufficient() {
        order.setServiceStatus(ServiceStatus.CLOSED);
        item1.setQuantity(15); // mais do que estoque atual
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setServiceStatus(ServiceStatus.CLOSED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        assertThrows(IllegalArgumentException.class, () -> orderService.update(1L, request));
        verify(productRepository, never()).save(product1);
    }
}
