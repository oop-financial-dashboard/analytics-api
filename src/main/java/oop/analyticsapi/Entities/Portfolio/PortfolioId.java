package oop.analyticsapi.Entities.Portfolio;

import java.io.Serializable;

public class PortfolioId implements Serializable {
    private String portfolioId;
    private String ticker;

    public PortfolioId(String portfolioId, String ticker) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
    }
}
