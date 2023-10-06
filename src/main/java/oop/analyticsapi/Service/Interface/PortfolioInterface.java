package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Domain.Models.Stock;

import java.util.List;

public interface PortfolioInterface {
    List<Stock> getAllStocksInPortfolio(String portfolioId);

}
