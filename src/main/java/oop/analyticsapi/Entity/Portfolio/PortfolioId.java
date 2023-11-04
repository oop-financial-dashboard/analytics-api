package oop.analyticsapi.Entity.Portfolio;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class PortfolioId implements Serializable {
    private String portfolioId;
    private String symbol;
    private String userId;

    public PortfolioId(String userId, String portfolioId, String symbol) {
        this.portfolioId = portfolioId;
        this.symbol = symbol;
        this.userId = userId;
    }
}
