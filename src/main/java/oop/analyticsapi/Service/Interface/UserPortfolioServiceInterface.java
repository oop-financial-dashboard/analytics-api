package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Domain.Models.TotalValue;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

public interface UserPortfolioServiceInterface {
    ResponseEntity<List<PortfolioEntity>> getAllPortfoliosByUser(String userId);
    ResponseEntity<String> createPortfolio(String userId, List<Stock> stocks);
    ResponseEntity<String> deletePortfolio(String userId, String portfolioId);
    //if user is using existing timeframe
    ResponseEntity<TotalValue> getPortfolioValue(String portfolioId);
    //if user is using new timeframe
    ResponseEntity<TotalValue> getPortfolioValue(String portfolioId, Timestamp start, Timestamp end);
    ResponseEntity<String> updatePortfolio(String portfolioId, Stock stocks, String action);
}
