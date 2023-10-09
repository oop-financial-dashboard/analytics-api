package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Domain.Models.Stock;

import java.util.List;

public interface PortfolioServiceInterface {
    List<Stock> getAllStocksInPortfolio(String portfolioId);
    Stock getStockInPortfolio(String portfolioId, String symbol);

}
