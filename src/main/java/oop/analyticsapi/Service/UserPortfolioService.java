package oop.analyticsapi.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.Data;
import oop.analyticsapi.Domain.Models.Portfolio;
import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Domain.ViewModel.AllPortfolios;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.PortfolioHistoricals.PortfolioValue;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import oop.analyticsapi.Enums.ErrorEnum;
import oop.analyticsapi.Exceptions.GenericException;
import oop.analyticsapi.Repository.*;
import oop.analyticsapi.Service.Interface.UserPortfolioServiceInterface;
import oop.analyticsapi.Enums.ActionEnum;
import org.antlr.v4.runtime.misc.Triple;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Data
@Service
public class UserPortfolioService implements UserPortfolioServiceInterface {
    private final PortfolioRepository portfolioRepository;
    private final StockDailyPriceRepository stockDailyPriceRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    private final PortfolioHistoricalValueRepository portfolioHistoricalValueRepository;
    @Autowired
    private UserPortfolioTransactional userPortfolio;
    @Autowired
    private PortfolioTransactional portfolio;
    private static final Logger logger = LoggerFactory.getLogger(UserPortfolioService.class);

    @Autowired
    public UserPortfolioService(
            PortfolioRepository portfolioRepository,
            StockDailyPriceRepository stockDailyPriceRepository,
            UserPortfolioRepository userPortfolioRepository,
            PortfolioHistoricalValueRepository portfolioHistoricalValueRepository
    ) {
        this.portfolioRepository = portfolioRepository;
        this.stockDailyPriceRepository = stockDailyPriceRepository;
        this.userPortfolioRepository = userPortfolioRepository;
        this.portfolioHistoricalValueRepository = portfolioHistoricalValueRepository;
    }

    @Override
    public AllPortfolios getAllPortfoliosByUser(String userId) {
        Map<String, Portfolio> portfolios = new HashMap<>();
        List<UserPortfolioEntity> portfolioIds = userPortfolioRepository.getAllPortfoliosByUserId(userId);
        for (UserPortfolioEntity pid : portfolioIds) {
            double totalValue = 0;
            List<PortfolioEntity> stocks = portfolioRepository
                    .getAllStocksInPortfolio(pid.getUserId(), pid.getPortfolioId());
            for (PortfolioEntity pe : stocks) {
                totalValue += pe.getValue();
            }
            Portfolio pf = Portfolio.builder()
                    .stocks(stocks)
                    .createdAt(pid.getCreatedAt())
                    .totalValue(totalValue)
                    .description(pid.getDescription())
                    .initialCapital(pid.getInitialCapital())
                    .build();

            portfolios.put(pid.getPortfolioId(), pf);
        }

        return AllPortfolios.builder()
                .userId(userId)
                .portfolios(portfolios)
                .build();
    }

    @Override
    @Transactional
    public String createNewPortfolio(String userId, String portfolioId, List<Stock> stocks, String description, Double initialCapital, LocalDate createdAt) throws NumberFormatException {
        //First insert new row in user_portfolio
        try {
            String res = userPortfolio.createUserPortfolioRecord(userId, portfolioId, description, initialCapital, createdAt);
//            LocalDate oneDayEarlier = createdAt.minusDays(1);
            insertPortfolioEntries(userId, stocks, portfolioId);
        } catch (Exception e) {
            throw new GenericException(ErrorEnum.ExistingPID);
        }
        return "Success";
    }

    @Override
    @Transactional
    public String deletePortfolio(String userId, String portfolioId) {
        String res = "Success";
        int deleteUserPortfolioEntry = userPortfolioRepository.deleteUserPortfolioEntry(userId, portfolioId);
        int deletePortfolioEntries = portfolioRepository.deletePortfolioEntry(portfolioId, userId);
        if (deleteUserPortfolioEntry == 0
                || deletePortfolioEntries == 0) {
            res = "Failed";
            logger.warn("Failed to delete portfolio!");
        }
        return res;
    }

    @Override
    @Transactional
    public String updatePortfolio(String userId, String portfolioId, String action, List<Stock> stocks,
                                  String description, Double initialCapital, LocalDate editedAt) {
        String res = "Success";
        //Update description and capital first
        if (initialCapital != null && description != null) {
            try {
                res = userPortfolio.updateUserPortfolio(userId, portfolioId, initialCapital, description);
            } catch(SQLException e) {
                logger.info("SQL Exception: " + e.getMessage());
            }
        }
        //Process updates
        ActionEnum actionEnum = ActionEnum.getActionFromString(action);
        switch (actionEnum) {
            case Add -> {
                //Add new portfolio entry (non-existing stock)
                res = insertPortfolioEntries(userId, stocks, portfolioId);
            }
            case Remove -> {
                for (Stock stock: stocks) {
                    int deletePortfolioEntry = portfolioRepository.deleteOnePortfolioEntry(userId, portfolioId, stock.getSymbol());
                    if (deletePortfolioEntry == 0) res = "Failed";
                }
            }
            case Increase -> {
                    //Get new price
                    for (Stock stock : stocks) {
                        double stockPrice = stock.getPrice();
                        Triplet<Integer, Double, Double> data = recalculateAvgCost(portfolioId, stock.getSymbol(), stock.getQuantity(), stockPrice);
                        try {
                            res = portfolio.updatePortfolioRecords(userId, portfolioId, data.getValue0(), stock.getSymbol(),
                                    data.getValue1(), data.getValue2());
                        } catch (SQLException e) {
                            logger.warn("Something went wrong with updatePortfolioRecords");
                        }
                    }
            }
        }
        return res;
    }

    @Override
    public List<PortfolioValue> getPortfolioHistoricals(String portfolioId, String userId) {
        return portfolioHistoricalValueRepository.getPortfolioHistoricals(portfolioId, userId);
    }


    private String insertPortfolioEntries(String userId, List<Stock> stocks, String portfolioId) {
        for (Stock stock : stocks) {
            String symbol = stock.getSymbol();
            //Get price on creation of portfolio
            int quantity = stock.getQuantity();
            double price = stock.getPrice();
            double totalValue = price * quantity;
            LocalDate dateAdded = stock.getDateAdded();
            try {
                portfolio.createPortfolioRecord(userId,portfolioId, quantity, symbol, price, totalValue, dateAdded);
            } catch (Exception e) {
                logger.warn("Double parse error!");
                return "Failed";
            }
        }
        return "Success";
    }

    private Triplet<Integer, Double, Double> recalculateAvgCost(String portfolioId, String symbol, int addedQuantity, double newPrice) {
        PortfolioEntity existingStockData = portfolioRepository.getOneStockInfo(portfolioId, symbol);
        double newTotalValue = (addedQuantity * newPrice) + existingStockData.getValue();
        int totalQty = addedQuantity + existingStockData.getQuantity();
        double newAvg = (newTotalValue / totalQty);

        return Triplet.with(totalQty, newAvg, newTotalValue);
    }

}
