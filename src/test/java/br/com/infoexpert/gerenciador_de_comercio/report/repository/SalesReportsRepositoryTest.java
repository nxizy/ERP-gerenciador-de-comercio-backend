package br.com.infoexpert.gerenciador_de_comercio.report.repository;

import br.com.infoexpert.gerenciador_de_comercio.client.repository.ClientRepository;
import br.com.infoexpert.gerenciador_de_comercio.employee.model.Employee;
import br.com.infoexpert.gerenciador_de_comercio.client.model.Client;
import br.com.infoexpert.gerenciador_de_comercio.employee.repository.EmployeeRepository;
import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.order.repository.OrderRepository;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.orderItem.repository.OrderItemRepository;
import br.com.infoexpert.gerenciador_de_comercio.owner.model.Owner;
import br.com.infoexpert.gerenciador_de_comercio.owner.repository.OwnerRepository;
import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class SalesReportsRepositoryTest {

    @Autowired
    private SalesReportsRepository reportsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setup() {
        Product product = productRepository.save(FixtureFactory.createProduct());
        Client client = clientRepository.save(FixtureFactory.createClient());
        Employee employee = employeeRepository.save(FixtureFactory.createEmployee());
        Owner owner = ownerRepository.save(FixtureFactory.createOwner());

        order1 = FixtureFactory.createOrder(client, employee, owner);
        order1.setFinishedAt(LocalDateTime.of(2025, 1, 10, 0, 0));

        OrderItem orderItem1 = FixtureFactory.createOrderItem(product, 3, BigDecimal.valueOf(200.00));
        orderItem1.setUnitPrice(BigDecimal.valueOf(200.00));
        orderItem1.setOrder(order1);

        order1.setItems(List.of(orderItem1));

        orderRepository.save(order1);

        order2 = FixtureFactory.createOrder(client, employee, owner);
        order2.setFinishedAt(LocalDateTime.of(2025, 2, 10, 0, 0));
        order2.setTotalAmount(BigDecimal.valueOf(80.00));

        OrderItem orderItem2 = FixtureFactory.createOrderItem(product, 3 , BigDecimal.valueOf(300.00));
        orderItem2.setOrder(order2);

        order2.setItems(List.of(orderItem2));

        orderRepository.save(order2);
    }


    @Test
    @DisplayName("Should return monthly sales Summary")
    void getMonthlySalesSummary() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        var result = reportsRepository.getMonthlySalesSummary(start, end, PageRequest.of(0, 10));


        List<MonthlySalesSummaryDTO> summaries = result.getContent();
        assertThat(summaries).hasSize(2);
        assertThat(summaries.get(0).getTotalValue()).isEqualByComparingTo("200.00");
        assertThat(summaries.get(1).getTotalValue()).isEqualByComparingTo("80.00");
    }

    @Test
    @DisplayName("Should return annual sales Summary")
    void getAnnualSalesSummary() {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 31, 23, 59);

        Page<AnnualSalesSummaryDTO> result = reportsRepository.getAnnualSalesSummary(start, end, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTotalValue()).isGreaterThan(BigDecimal.valueOf(0));
    }

    @Test
    @DisplayName("Should return month profit")
    void getMonthlyProfit() {
        List<Object[]> rawList = reportsRepository.getMonthlyProfit(1, 2025);
        Object[] raw = rawList.get(0);

                MonthlyProfitDTO profit = new MonthlyProfitDTO(
                ((Number) raw[0]).intValue(),
                ((Number) raw[1]).intValue(),
                ((Number) raw[2]).longValue(),
                new BigDecimal(raw[3].toString()),
                new BigDecimal(raw[4].toString()),
                new BigDecimal(raw[5].toString())
        );




        assertThat(profit).isNotNull();
        assertThat(profit.getTotalRevenue()).isGreaterThan(BigDecimal.valueOf(0));
        assertThat(profit.getTotalProfit()).isGreaterThanOrEqualTo(BigDecimal.valueOf(0));
    }
}
