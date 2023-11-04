package oop.analyticsapi.Domain.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
public class PortfolioUserIds {
    private String userId;
    private String portfolioId;

    public PortfolioUserIds(String userId, String portfolioId) {
        this.userId = userId;
        this.portfolioId = portfolioId;
    }
}
