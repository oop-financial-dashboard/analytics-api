package oop.analyticsapi.Entity.Portfolio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PortfolioId.class)
@Table(name = "portfolio")
public class PortfolioEntity {
    @Id
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "total_value")
    private double value;
}