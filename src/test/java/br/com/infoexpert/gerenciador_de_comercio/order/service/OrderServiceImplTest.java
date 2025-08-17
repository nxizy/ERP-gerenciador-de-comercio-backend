package br.com.infoexpert.gerenciador_de_comercio.order.service;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.client.repository.ClientRepository;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.repository.EmployeeRepository;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.repository.OrderRepository;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.repository.OwnerRepository;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Client client = FixtureFactory.createClient(1L);
    Employee employee = FixtureFactory.createEmployee(1L);
    Owner owner = FixtureFactory.createOwner(1L);

    @Test
    @DisplayName("Should create order with items, resolving products")
    void shouldCreateOrder() {
        CreateOrderRequest request = FixtureFactory.createOrderRequest(client, employee, owner);

        when(clientRepository.findById(request.getClientId())).thenReturn(Optional.of(client));
        when(employeeRepository.findById(request.getEmployeeId())).thenReturn(Optional.of(employee));
        when(ownerRepository.findById(request.getOwnerId())).thenReturn(Optional.of(owner));

        request.getItems().forEach(itemReq -> {
            Product p = itemReq.getProduct();
            when(productRepository.findById(p.getId())).thenReturn(Optional.of(p));
        });

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(1L);
            return o;
        });

        Order result = orderService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getClient()).isEqualTo(client);
        assertThat(result.getEmployee()).isEqualTo(employee);
        assertThat(result.getOwner()).isEqualTo(owner);
        assertThat(result.getItems()).isNotEmpty();

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should update order")
    void shouldUpdateOrder() {
        Long id = 1L;
        Order existingOrder = FixtureFactory.createOrder(1L, client, employee, owner);

        UpdateOrderRequest request = FixtureFactory.updateOrderRequest();

        when(orderRepository.findById(id)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        Order response = orderService.update(id, request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getIsPaid()).isEqualTo(request.getIsPaid());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should delete order")
    void shouldDeleteOrder() {
        Long id = 1L;
        when(orderRepository.existsById(id)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(id);

        orderService.delete(id);

        verify(orderRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should find all orders")
    void shouldFindAllOrders() {
        Pageable pageable = Pageable.ofSize(10);
        var page = FixtureFactory.createOrderPage(client, employee, owner);

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = orderService.findAll(pageable);

        assertThat(result.getContent()).hasSize(3);
        verify(orderRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should find order by id")
    void shouldFindOrderById() {
        Long id = 1L;
        Order order = FixtureFactory.createOrder(1L, client, employee, owner);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = orderService.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(orderRepository).findById(id);
    }

    @Test
    @DisplayName("Should find orders using Specification filters")
    void shouldFindOrdersWithSpecification() {
        Pageable pageable = Pageable.ofSize(10);

        OrderFilterDTO filter = OrderFilterDTO.builder()
                .clientId(client.getId())
                .clientName("John Doe")
                .serviceStatus(ServiceStatus.IN_PROGRESS)
                .employeeId(employee.getId())
                .paymentSituation(false)
                .paymentMethod(PaymentMethod.CASH)
                .build();

        Order order1 = FixtureFactory.createOrder(1L, client, employee, owner);
        Order order2 = FixtureFactory.createOrder(2L, client, employee, owner);

        var page = new PageImpl<>(List.of(order1, order2));

        when(orderRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        var result = orderService.findOrders(filter, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).contains(order1, order2);
        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
    }
}
