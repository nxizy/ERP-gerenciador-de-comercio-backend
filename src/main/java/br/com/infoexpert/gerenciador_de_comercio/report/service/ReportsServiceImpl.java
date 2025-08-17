package br.com.infoexpert.gerenciador_de_comercio.report.service;

import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.AnnualSalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlyProfitDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.sales.MonthlySalesSummaryDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import br.com.infoexpert.gerenciador_de_comercio.report.repository.SalesReportsRepository;
import br.com.infoexpert.gerenciador_de_comercio.report.repository.StockReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final SalesReportsRepository salesReportsRepository;
    private final StockReportsRepository stockReportsRepository;

    @Override
    public Page<StockReportDTO> getLowStockProducts(Pageable pageable) {
        return stockReportsRepository.getLowStockProducts(pageable);
    }

    @Override
    public Page<MonthlySalesSummaryDTO> getSalesSummaryByMonth(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if(start.isAfter(end)){
            throw new IllegalArgumentException("Start date must be after end date");
        }
        return salesReportsRepository.getMonthlySalesSummary(start, end, pageable);
    }

    @Override
    public Page<AnnualSalesSummaryDTO> getSalesSummaryByYear(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if(start.isAfter(end)){
            throw new IllegalArgumentException("Start date must be after end date");
        }
        return salesReportsRepository.getAnnualSalesSummary(start, end, pageable);
    }

    @Override
    public MonthlyProfitDTO getProfitByMonth(int month, int year) {
        List<Integer> validMonths = Stream.of(Month.values()).map(Month::getValue).toList();
        int currentYear = LocalDate.now().getYear();

        if(!validMonths.contains(month)){
            throw new IllegalArgumentException("Invalid month");
        }
        if(year < 2000 || year > currentYear){
            throw new IllegalArgumentException("Invalid year");
        }

        List<Object[]> rawList = salesReportsRepository.getMonthlyProfit(1, 2025);
        Object[] raw = rawList.get(0);

        return new MonthlyProfitDTO(
                ((Number) raw[0]).intValue(),
                ((Number) raw[1]).intValue(),
                ((Number) raw[2]).longValue(),
                new BigDecimal(raw[3].toString()),
                new BigDecimal(raw[4].toString()),
                new BigDecimal(raw[5].toString())
        );
    }
}
