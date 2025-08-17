package br.com.infoexpert.gerenciador_de_comercio.order.repository;

import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
