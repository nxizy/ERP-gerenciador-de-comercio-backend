package br.com.infoexpert.gerenciador_de_comercio.report.repository;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockReportsRepository extends JpaRepository<Product, Integer> {
    @Query("""
        SELECT new br.com.infoexpert.gerenciador_de_comercio.report.model.dto.stock.StockReportDTO(
            p.id,
            p.name,
            p.stockQuantity
        )
        FROM Product p
        ORDER BY p.stockQuantity ASC
    """)
    Page<StockReportDTO> getLowStockProducts(Pageable pageable);
}
