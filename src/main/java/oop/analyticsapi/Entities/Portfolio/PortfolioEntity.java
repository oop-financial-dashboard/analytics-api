package oop.analyticsapi.Entities.Portfolio;

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
    private String ticker;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "quantity")
    private Integer quantity;
}