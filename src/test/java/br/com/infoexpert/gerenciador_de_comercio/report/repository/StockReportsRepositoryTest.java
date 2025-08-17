package br.com.infoexpert.gerenciador_de_comercio.report.repository;

import br.com.infoexpert.gerenciador_de_comercio.product.model.Product;
import br.com.infoexpert.gerenciador_de_comercio.product.repository.ProductRepository;
import br.com.infoexpert.gerenciador_de_comercio.utils.FixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
class StockReportsRepositoryTest {

    @Autowired
    private StockReportsRepository reportsRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product1 = FixtureFactory.createProduct();

        productRepository.save(product1);

        Product product2 = FixtureFactory.createProduct();
        product2.setStockQuantity(1);

        productRepository.save(product2);
    }

    @Test
    @DisplayName("Should return low stock products")
    void shouldGetLowStockProducts() {
        //when
        var result = reportsRepository.getLowStockProducts(PageRequest.of(0, 10));

        //then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getStockQuantity()).isEqualTo(1);
        System.out.println(result.getContent());
    }

}
