package br.com.infoexpert.gerenciador_de_comercio.report.service;

import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.repository.SalesReportsRepository;
import br.com.infoexpert.gerenciador_de_comercio.report.repository.StockReportsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReportsServiceImplTest {

    @Mock
    private SalesReportsRepository salesReportsRepository;

    @Mock
    private StockReportsRepository stockReportsRepository;

    @InjectMocks
    private ReportsServiceImpl reportsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return low stock products")
    void shouldReturnLowStockProducts() {
        Pageable pageable = Pageable.ofSize(5);
        Page<StockReportDTO> page = new PageImpl<>(List.of(new StockReportDTO(1L, "Produto X", 2)));

        when(stockReportsRepository.getLowStockProducts(pageable)).thenReturn(page);

        Page<StockReportDTO> result = reportsService.getLowStockProducts(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getProductName()).isEqualTo("Produto X");
        verify(stockReportsRepository).getLowStockProducts(pageable);
    }

    @Test
    @DisplayName("Should return monthly sales summary when dates are valid")
    void shouldReturnMonthlySalesSummary() {
        Pageable pageable = Pageable.ofSize(10);
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now();
        Page<MonthlySalesSummaryDTO> page = new PageImpl<>(List.of(new MonthlySalesSummaryDTO(1, 2025, 10L, BigDecimal.TEN)));

        when(salesReportsRepository.getMonthlySalesSummary(start, end, pageable)).thenReturn(page);

        Page<MonthlySalesSummaryDTO> result = reportsService.getSalesSummaryByMonth(start, end, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(salesReportsRepository).getMonthlySalesSummary(start, end, pageable);
    }

    @Test
    @DisplayName("Should throw when start date is after end date in monthly summary")
    void shouldThrowWhenStartAfterEnd_Monthly() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusDays(1);

        assertThatThrownBy(() -> reportsService.getSalesSummaryByMonth(start, end, Pageable.ofSize(5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date must be after end date");
    }

    @Test
    @DisplayName("Should return annual sales summary when dates are valid")
    void shouldReturnAnnualSalesSummary() {
        Pageable pageable = Pageable.ofSize(10);
        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = LocalDateTime.now();
        Page<AnnualSalesSummaryDTO> page = new PageImpl<>(List.of(new AnnualSalesSummaryDTO(2025, 120L, BigDecimal.TEN)));

        when(salesReportsRepository.getAnnualSalesSummary(start, end, pageable)).thenReturn(page);

        Page<AnnualSalesSummaryDTO> result = reportsService.getSalesSummaryByYear(start, end, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(salesReportsRepository).getAnnualSalesSummary(start, end, pageable);
    }

    @Test
    @DisplayName("Should throw when start date is after end date in annual summary")
    void shouldThrowWhenStartAfterEnd_Annual() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusDays(1);

        assertThatThrownBy(() -> reportsService.getSalesSummaryByYear(start, end, Pageable.ofSize(5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date must be after end date");
    }

    @Test
    @DisplayName("Should return monthly profit when month and year are valid")
    void shouldReturnMonthlyProfit() {
        Object[] raw = {1, 2025, 10L, BigDecimal.valueOf(100), BigDecimal.valueOf(50), BigDecimal.valueOf(50)};
        when(salesReportsRepository.getMonthlyProfit(1, 2025)).thenReturn(List.<Object[]>of(raw));

        MonthlyProfitDTO result = reportsService.getProfitByMonth(1, 2025);

        assertThat(result.getMonth()).isEqualTo(1);
        assertThat(result.getYear()).isEqualTo(2025);
        assertThat(result.getTotalRevenue()).isEqualByComparingTo(BigDecimal.valueOf(100));
        verify(salesReportsRepository).getMonthlyProfit(1, 2025);
    }

    @Test
    @DisplayName("Should throw when month is invalid")
    void shouldThrowWhenMonthInvalid() {
        assertThatThrownBy(() -> reportsService.getProfitByMonth(13, 2025))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid month");
    }

    @Test
    @DisplayName("Should throw when year is invalid")
    void shouldThrowWhenYearInvalid() {
        assertThatThrownBy(() -> reportsService.getProfitByMonth(1, 1999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid year");
    }
}
