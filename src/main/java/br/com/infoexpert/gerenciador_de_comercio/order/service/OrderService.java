package br.com.infoexpert.gerenciador_de_comercio.order.service;

import br.com.infoexpert.gerenciador_de_comercio.enums.PaymentMethod;
import br.com.infoexpert.gerenciador_de_comercio.enums.ServiceStatus;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.CreateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderFilterDTO;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.UpdateOrderRequest;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderService {
    Order create(CreateOrderRequest request);

    Order update(Long id ,UpdateOrderRequest request);

    void delete(Long id);

    Page<Order> findAll(Pageable pageable);

    Order findById(Long id);

    Page<Order> findOrders(OrderFilterDTO orderFilterDTO, Pageable pageable);


}
