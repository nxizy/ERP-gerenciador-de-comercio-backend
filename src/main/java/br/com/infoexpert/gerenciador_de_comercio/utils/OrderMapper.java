package br.com.infoexpert.gerenciador_de_comercio.utils;

import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.dto.EmployeeResponse;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.model.dto.OrderResponse;
import org.springframework.data.domain.Page;

public class OrderMapper {
    public static Page<OrderResponse> toOrderResponsePage(Page<Order> orders) {
        return orders.map(Order::toOrderResponse);
    }
}
