package br.com.infoexpert.gerenciador_de_comercio.product.model;

import br.com.infoexpert.gerenciador_de_comercio.orderItem.model.OrderItem;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.ProductResponse;
import br.com.infoexpert.gerenciador_de_comercio.product.model.dto.UpdateProductRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Integer stockQuantity;

    private String NCM;

    private String CFOP;

    private String productCode;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;


    public void merge(UpdateProductRequest request) {
        if(request.getName() != null) this.name = request.getName();
        if(request.getDescription() != null) this.description = request.getDescription();
        if(request.getPrice() != null) this.price = request.getPrice();
        if(request.getStockQuantity() != null) this.stockQuantity = request.getStockQuantity();
        if(request.getProductCode() != null) this.productCode = request.getProductCode();
        if(request.getNCM() != null) this.NCM = request.getNCM();
        if(request.getCFOP() != null) this.CFOP = request.getCFOP();

    }

    public ProductResponse toProductResponse(){
        return ProductResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .productCode(this.productCode)
                .NCM(this.NCM)
                .CFOP(this.CFOP)
                .build();
    }

}
