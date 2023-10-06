package oop.analyticsapi.Service;

import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Domain.Models.TotalValue;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Service.Interface.UserPortfolioServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserPortfolioService implements UserPortfolioServiceInterface {

    @Override
    public ResponseEntity<List<PortfolioEntity>> getAllPortfoliosByUser(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<String> createPortfolio(String userId, List<Stock> stocks) {
        return null;
    }

    @Override
    public ResponseEntity<String> deletePortfolio(String userId, String portfolioId) {
        return null;
    }

    @Override
    public ResponseEntity<TotalValue> getPortfolioValue(String portfolioId) {
        return null;
    }

    @Override
    public ResponseEntity<TotalValue> getPortfolioValue(String portfolioId, Timestamp start, Timestamp end) {
        return null;
    }

    @Override
    public ResponseEntity<String> updatePortfolio(String portfolioId, Stock stocks, String action) {
        return null;
    }
}
