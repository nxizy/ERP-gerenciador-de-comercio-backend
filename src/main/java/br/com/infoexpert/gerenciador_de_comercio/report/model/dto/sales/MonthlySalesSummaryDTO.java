package br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthlySalesSummaryDTO {
    private Integer month;
    private Integer year;
    private Long totalSales;
    private BigDecimal totalValue;
}
