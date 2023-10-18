package oop.analyticsapi.Service;

import jakarta.transaction.Transactional;
import lombok.Data;
import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import oop.analyticsapi.Repository.*;
import oop.analyticsapi.Service.Interface.UserPortfolioServiceInterface;
import oop.analyticsapi.Enums.ActionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserPortfolioService implements UserPortfolioServiceInterface {
    private final PortfolioRepository portfolioRepository;
    private final StockDailyPriceRepository stockDailyPriceRepository;
    private final UserPortfolioRepository userPortfolioRepository;
    @Autowired
    private UserPortfolio userPortfolio;
    @Autowired
    private Portfolio portfolio;
    private static final Logger logger = LoggerFactory.getLogger(UserPortfolioService.class);

    @Autowired
    public UserPortfolioService(
            PortfolioRepository portfolioRepository,
            StockDailyPriceRepository stockDailyPriceRepository,
            UserPortfolioRepository userPortfolioRepository
    ) {
        this.portfolioRepository = portfolioRepository;
        this.stockDailyPriceRepository = stockDailyPriceRepository;
        this.userPortfolioRepository = userPortfolioRepository;
    }

    @Override
    public List<UserPortfolioEntity> getAllPortfoliosByUser(String userId) {
        return userPortfolioRepository.getAllPortfoliosByUserId(userId);
    }

    @Override
    @Transactional
    public String createNewPortfolio(String userId, String portfolioId, List<Stock> stocks, LocalDate createdAt) throws NumberFormatException {
        //First insert new row in user_portfolio
        try {
           String res = userPortfolio.createUserPortfolioRecord(userId, portfolioId, createdAt);
           if (res.equals("Failed")) return res;
           LocalDate oneDayEarlier = createdAt.minusDays(1);
           String status = insertPortfolioEntries(stocks, portfolioId, oneDayEarlier);
           if (status.equals("Failed")) return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Success";
    }

    @Override
    @Transactional
    public String deletePortfolio(String userId, String portfolioId) {
        String res = "Success";
        int deleteUserPortfolioEntry = userPortfolioRepository.deleteUserPortfolioEntry(userId, portfolioId);
        int deletePortfolioEntries = portfolioRepository.deletePortfolioEntry(portfolioId);
        if (deleteUserPortfolioEntry == 0
            || deletePortfolioEntries == 0) {
            res = "Failed";
            logger.warn("Failed to delete portfolio!");
        }
        return res;
    }

    @Override
    @Transactional
    public String updatePortfolio(String userId, String portfolioId, String action, Stock stock, LocalDate editedAt,
                                  Optional<Integer> addedQuantity) {
        String res = "Success";
        ActionEnum actionEnum = ActionEnum.getActionFromString(action);


        LocalDate oneDayEarlier = editedAt.minusDays(1);

        switch (actionEnum) {
            case Add -> {
                //Hack
                List<Stock> stockArray = new ArrayList<>(1);
                stockArray.add(stock);

                String addPortfolioEntry = insertPortfolioEntries(stockArray, portfolioId, oneDayEarlier);

            }
            case Remove -> {
                int deletePortfolioEntry = portfolioRepository.deletePortfolioEntry(portfolioId);
                if (deletePortfolioEntry == 0) res = "Failed";

                portfolioRepository.save(
                        PortfolioEntity
                                .builder()
                                .portfolioId(portfolioId)
                                .symbol(stock.getSymbol())
                                .build()
                );
            }
            case Increase -> {
                try {
                    if (addedQuantity.isPresent()) {
                        int quantity = addedQuantity.get();
                        //Get new price
                        Optional<StockDailyPriceEntity> stockData = stockDailyPriceRepository.getStockDailyPriceBySymbol(stock.getSymbol(), oneDayEarlier);
                        if (stockData.isPresent()) {
                            int stockPrice = Integer.parseInt(stockData.get().getClose());
                            List<Double> newAvgCost = recalculateAvgCost(portfolioId, stock.getSymbol(), quantity, stockPrice);
                            int increasePortfolioEntry = portfolioRepository.updatePortfolioEntry(portfolioId, quantity, stock.getSymbol(),
                                    newAvgCost.get(1), newAvgCost.get(0));

                            if (increasePortfolioEntry == 0) res = "Failed";
                        } else {
                            logger.warn("No stock data found for {}", stock.getSymbol());
                        }
                    } else {
                        logger.warn("Invalid Params: No Quantity found");
                    }
                } catch (Exception e) {
                    logger.warn("Parse Int error!");
                    throw e;
                }
            }
        }
        return res;
    }


    private String insertPortfolioEntries(List<Stock> stocks, String portfolioId, LocalDate createdAt) {
        for (Stock stock : stocks) {
            String symbol = stock.getSymbol();
            //Get price on creation of portfolio
            int quantity = stock.getQuantity();
            Optional<StockDailyPriceEntity> stockPrice = stockDailyPriceRepository.getStockDailyPriceBySymbol(symbol, createdAt);
            if (stockPrice.isPresent()) {
                StockDailyPriceEntity stockData = stockPrice.get();
                try {
                    double close = Double.parseDouble(stockData.getClose());
                    double value = (double) quantity * close;
                    portfolio.createPortfolioRecord(portfolioId, quantity, symbol, close, value);
                } catch (Exception e) {
                    logger.warn("Double parse error!");
                    return "Failed";
                }
            } else {
                logger.warn("No stock price data related to {} found!", symbol);
            }
        }
        return "Success";
    }

    private List<Double> recalculateAvgCost(String portfolioId, String symbol, int addedQuantity, double newPrice) {
        List<Double> res = new ArrayList<>(2);
        PortfolioEntity existingStockData = portfolioRepository.getOneStockInfo(portfolioId, symbol);

        double newTotalValue = (addedQuantity * newPrice) + existingStockData.getValue();
        res.add(newTotalValue);
        res.add((newTotalValue / (addedQuantity + existingStockData.getQuantity())));

        return res;
    }

}
