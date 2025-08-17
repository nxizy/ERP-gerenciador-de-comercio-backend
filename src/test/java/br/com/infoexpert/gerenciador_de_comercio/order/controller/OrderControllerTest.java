package br.com.infoexpert.gerenciador_de_comercio.order.controller;

import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.service.OrderService;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;
    private Employee employee;
    private Owner owner;

    @BeforeEach
    void setUp() {
        client = FixtureFactory.createClient(1L);
        employee = FixtureFactory.createEmployee(1L);
        owner = FixtureFactory.createOwner(1L);
    }

    @Test
    @DisplayName("POST /api/orders should create order")
    void shouldCreateOrder() throws Exception {
        CreateOrderRequest request = FixtureFactory.createOrderRequest(client, employee, owner);
        Order order = FixtureFactory.createOrder(1L, client, employee, owner);

        when(orderService.create(any())).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/orders/{id} should update order")
    void shouldUpdateOrder() throws Exception {
        UpdateOrderRequest request = FixtureFactory.updateOrderRequest();
        Order updatedOrder = FixtureFactory.createOrder(1L, client, employee, owner);
        updatedOrder.setServiceStatus(ServiceStatus.CLOSED);

        when(orderService.update(eq(1L), any())).thenReturn(updatedOrder);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceStatus").value("CLOSED"));
    }

    @Test
    @DisplayName("DELETE /api/orders/{id} should delete order")
    void shouldDeleteOrder() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/orders with no filters should return all orders")
    void shouldReturnAllOrders() throws Exception {
        Order order = FixtureFactory.createOrder(1L, client, employee, owner);

        when(orderService.findAll(any())).thenReturn(new PageImpl<>(List.of(order)));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/orders with filters should use findOrders correctly")
    void shouldFilterOrdersUsingSpecification() throws Exception {
        Order order = FixtureFactory.createOrder(1L, client, employee, owner);

        ArgumentCaptor<OrderFilterDTO> captor = ArgumentCaptor.forClass(OrderFilterDTO.class);

        when(orderService.findOrders(captor.capture(), any()))
                .thenReturn(new PageImpl<>(List.of(order)));

        mockMvc.perform(get("/api/orders")
                        .param("clientName", "John")
                        .param("serviceStatus", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));

        OrderFilterDTO capturedFilter = captor.getValue();
        assertThat(capturedFilter.getClientName()).isEqualTo("John");
        assertThat(capturedFilter.getServiceStatus()).isEqualTo(ServiceStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("GET /api/orders/{id} should return a single order")
    void shouldReturnOrderById() throws Exception {
        Order order = FixtureFactory.createOrder(1L, client, employee, owner);

        when(orderService.findById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
