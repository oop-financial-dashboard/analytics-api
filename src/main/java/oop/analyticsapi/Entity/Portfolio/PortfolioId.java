package oop.analyticsapi.Entity.Portfolio;

import java.io.Serializable;

public class PortfolioId implements Serializable {
    private String portfolioId;
    private String symbol;

    public PortfolioId(String portfolioId, String symbol) {
        this.portfolioId = portfolioId;
        this.symbol = symbol;
    }
}
