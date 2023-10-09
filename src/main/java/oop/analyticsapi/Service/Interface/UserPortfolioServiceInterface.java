package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserPortfolioServiceInterface {
    List<UserPortfolioEntity>getAllPortfoliosByUser(String userId);

    String createNewPortfolio(String userId, String portfolioId, List<Stock> stocks, Timestamp createdAt);

    String deletePortfolio(String userId, String portfolioId);

    String updatePortfolio(
            String userId,
            String portfolioId,
            String action,
            Stock stock,
            Timestamp editedAt,
            Optional<Integer> addedQuantity
    );


}
