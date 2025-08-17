package br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReportDTO {
    private Long productId;
    private String productName;
    private Integer stockQuantity;
}
