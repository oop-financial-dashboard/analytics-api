package oop.analyticsapi.Entity.UserPortfolio;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserPortfolioId implements Serializable {
    private String portfolioId;

    public UserPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

}
