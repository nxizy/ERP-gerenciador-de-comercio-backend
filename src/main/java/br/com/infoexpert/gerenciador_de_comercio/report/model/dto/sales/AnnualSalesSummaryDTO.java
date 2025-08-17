package br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnualSalesSummaryDTO {
    private Integer year;
    private Long totalSales;
    private BigDecimal totalValue;
}
