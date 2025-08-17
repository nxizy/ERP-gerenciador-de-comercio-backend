package br.com.infoexpert.gerenciador_de_comercio.report.controller;

import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static br.com.infoexpert.gerenciador_de_comercio.config.ApiPaths.REPORTS;

@RestController
@RequestMapping(REPORTS)
@RequiredArgsConstructor
@Tag(name = "Reports Management", description = "Operations for retrieving reports")
public class ReportsController {

    private final ReportsService reportsService;

    @Operation(summary = "Get all the low stock products in descending order")
    @GetMapping("/low-stock-report")
    public ResponseEntity<Page<StockReportDTO>> getLowStockProducts(Pageable pageable) {
        Page<StockReportDTO> page = reportsService.getLowStockProducts(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get a sales summary filtered by month")
    @GetMapping("/sales-by-month-report")
    public ResponseEntity<Page<MonthlySalesSummaryDTO>> getSalesSummaryByMonth(
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {
        Page<MonthlySalesSummaryDTO> page = reportsService.getSalesSummaryByMonth(start, end, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get sales summary filtered by year")
    @GetMapping("/sales-by-year-report")
    public ResponseEntity<Page<AnnualSalesSummaryDTO>> getSalesSummaryByYear(
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {
        Page<AnnualSalesSummaryDTO> page = reportsService.getSalesSummaryByYear(start, end, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get month profit report")
    @GetMapping("/month-profit-report")
    public ResponseEntity<MonthlyProfitDTO> getMonthProfit(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(reportsService.getProfitByMonth(month, year));
    }
}
