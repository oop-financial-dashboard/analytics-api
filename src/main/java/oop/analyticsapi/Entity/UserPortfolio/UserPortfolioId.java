package oop.analyticsapi.Entity.UserPortfolio;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserPortfolioId implements Serializable {
    private String portfolioId;
    private String userId;

    public UserPortfolioId(String portfolioId, String userId) {
        this.portfolioId = portfolioId;
        this.userId = userId;
    }

}
