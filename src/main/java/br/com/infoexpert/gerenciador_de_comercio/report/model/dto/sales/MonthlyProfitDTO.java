package br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyProfitDTO {
    private Integer month;
    private Integer year;
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal totalCost;
    private BigDecimal totalProfit;

    public MonthlyProfitDTO(Integer month, Integer year, Long totalOrders, BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal totalProfit) {
        this.month = month;
        this.year = year;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.totalProfit = totalProfit;
    }
}
