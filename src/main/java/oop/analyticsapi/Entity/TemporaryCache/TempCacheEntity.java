package oop.analyticsapi.Entity.TemporaryCache;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioId;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserPortfolioId.class)
@Table(name = "update_cache")
public class TempCacheEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "total_value")
    private double value;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "action")
    private LocalDate action;


}
