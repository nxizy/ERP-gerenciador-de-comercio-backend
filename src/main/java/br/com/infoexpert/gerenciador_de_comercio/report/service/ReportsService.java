package br.com.infoexpert.gerenciador_de_comercio.report.service;

import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReportsService {

    Page<StockReportDTO> getLowStockProducts(Pageable pageable);

    Page<MonthlySalesSummaryDTO> getSalesSummaryByMonth(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<AnnualSalesSummaryDTO> getSalesSummaryByYear(LocalDateTime start, LocalDateTime end, Pageable pageable);

    MonthlyProfitDTO getProfitByMonth(int month, int year);
}
