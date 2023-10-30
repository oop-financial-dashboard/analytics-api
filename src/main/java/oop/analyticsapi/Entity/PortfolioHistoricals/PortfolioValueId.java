package oop.analyticsapi.Entity.PortfolioHistoricals;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
public class PortfolioValueId implements Serializable {
    private String portfolioId;
    private LocalDate date;

    public PortfolioValueId(String portfolioId, LocalDate date) {
        this.date = date;
        this.portfolioId = portfolioId;
    }
}