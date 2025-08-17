package br.com.infoexpert.gerenciador_de_comercio.orderItem.repository;

import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
