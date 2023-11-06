package oop.analyticsapi.Entity.PortfolioHistoricals;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
public class PortfolioValueId implements Serializable {
    private String userId;
    private String portfolioId;
    private String symbol;
    private LocalDate date;

    public PortfolioValueId(String userId, String portfolioId, String symbol, LocalDate date, String rng) {
        this.date = date;
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.symbol = symbol;
    }
}
