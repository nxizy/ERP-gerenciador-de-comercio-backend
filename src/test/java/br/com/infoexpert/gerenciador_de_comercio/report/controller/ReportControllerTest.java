package br.com.infoexpert.gerenciador_de_comercio.report.controller;

import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.service.ReportsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportsControllerTest {

    @Autowired
    private ReportsController reportsController;

    @MockBean
    private ReportsService reportsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reportsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("GET /api/reports/low-stock-report")
    void shouldReturnLowStockProducts() throws Exception {
        StockReportDTO stockReport = new StockReportDTO(1L, "Produto X", 2);
        when(reportsService.getLowStockProducts(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(stockReport)));


        mockMvc.perform(get("/api/reports/low-stock-report")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productName").value("Produto X"))
                .andExpect(jsonPath("$.content[0].stockQuantity").value(2));
    }

    @Test
    @DisplayName("GET /api/reports/sales-by-month-report")
    void shouldReturnSalesSummaryByMonth() throws Exception {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 1, 31, 23, 59);

        MonthlySalesSummaryDTO monthSummary = new MonthlySalesSummaryDTO(1, 2025, 10L, BigDecimal.valueOf(1500.00));
        when(reportsService.getSalesSummaryByMonth(eq(start), eq(end), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(monthSummary)));

        mockMvc.perform(get("/api/reports/sales-by-month-report")
                        .param("start", start.toString())
                        .param("end", end.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].month").value(1))
                .andExpect(jsonPath("$.content[0].year").value(2025));
    }

    @Test
    @DisplayName("GET /api/reports/sales-by-year-report")
    void shouldReturnSalesSummaryByYear() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 12, 31, 23, 59);

        AnnualSalesSummaryDTO annualSummary = new AnnualSalesSummaryDTO(2025, 10L, BigDecimal.valueOf(105500.00));
        when(reportsService.getSalesSummaryByYear(eq(startDate), eq(endDate), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(annualSummary)));

        mockMvc.perform(get("/api/reports/sales-by-year-report")
                        .param("start", startDate.toString())
                        .param("end", endDate.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].year").value(2025))
                .andExpect(jsonPath("$.content[0].totalSales").value(10));
    }

    @Test
    @DisplayName("GET /api/reports/month-profit-report")
    void shouldReturnMonthlyProfit() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reportsController).build();

        MonthlyProfitDTO dto = new MonthlyProfitDTO(
                1, 2025, 10L,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(50)
        );

        when(reportsService.getProfitByMonth(1, 2025)).thenReturn(dto);

        mockMvc.perform(get("/api/reports/month-profit-report")
                        .param("month", "1")
                        .param("year", "2025")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.month").value(1))
                .andExpect(jsonPath("$.year").value(2025))
                .andExpect(jsonPath("$.totalOrders").value(10))
                .andExpect(jsonPath("$.totalRevenue").value(100))
                .andExpect(jsonPath("$.totalCost").value(50))
                .andExpect(jsonPath("$.totalProfit").value(50));
    }
}
