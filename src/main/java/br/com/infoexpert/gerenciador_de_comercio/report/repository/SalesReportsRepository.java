package br.com.infoexpert.gerenciador_de_comercio.report.repository;

import br.com.infoexpert.gerenciador_de_comercio.order.model.Order;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesReportsRepository extends JpaRepository<Order, Long> {
    @Query("""
SELECT new br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO(
    MONTH(o.finishedAt),
    YEAR(o.finishedAt),
    COUNT(o.id),
    SUM(o.totalAmount)
)
FROM Order o
WHERE o.createdAt BETWEEN :start AND :end
GROUP BY YEAR(o.finishedAt), MONTH(o.finishedAt)
ORDER BY YEAR(o.finishedAt), MONTH(o.finishedAt)
""")
    Page<MonthlySalesSummaryDTO> getMonthlySalesSummary(@Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end, Pageable pageable);





    @Query("""
    SELECT new br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO(
        YEAR(o.finishedAt),
        COUNT(o.id),
        SUM(o.totalAmount)
    )
    FROM Order o
    WHERE o.createdAt BETWEEN :start AND :end
    GROUP BY YEAR(o.finishedAt)
    ORDER BY YEAR(o.finishedAt)
""")
    Page<AnnualSalesSummaryDTO> getAnnualSalesSummary(@Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end, Pageable pageable);


    @Query(value = """
    SELECT 
        EXTRACT(MONTH FROM o.finished_at) AS month,
        EXTRACT(YEAR FROM o.finished_at) AS year,
        COUNT(o.id) AS totalOrders,
        SUM(o.service_price + (oi.unit_price * oi.quantity)) AS grossRevenue,
        SUM(oi.quantity * p.price) AS cost,
        SUM((o.service_price + (oi.unit_price * oi.quantity)) - (oi.quantity * p.price)) AS profit
    FROM orders o
    LEFT JOIN order_items oi ON o.id = oi.order_id
    LEFT JOIN products p ON p.id = oi.product_id
    WHERE o.finished_at IS NOT NULL
      AND o.is_paid = true
      AND EXTRACT(MONTH FROM o.finished_at) = :month
      AND EXTRACT(YEAR FROM o.finished_at) = :year
    GROUP BY EXTRACT(YEAR FROM o.finished_at), EXTRACT(MONTH FROM o.finished_at)
""", nativeQuery = true)
    List<Object[]> getMonthlyProfit(@Param("month") int month, @Param("year") int year);


}
