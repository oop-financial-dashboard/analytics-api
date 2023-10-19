package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Domain.ViewModel.AllPortfolios;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserPortfolioServiceInterface {
    AllPortfolios getAllPortfoliosByUser(String userId);

    String createNewPortfolio(String userId, String portfolioId, List<Stock> stocks, LocalDate createdAt);

    String deletePortfolio(String userId, String portfolioId);

    String updatePortfolio(
            String userId,
            String portfolioId,
            String action,
            Stock stock,
            LocalDate editedAt
    );


}
