package oop.analyticsapi.Service;

import jakarta.transaction.Transactional;
import lombok.Data;
import oop.analyticsapi.Domain.Models.Stock;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import oop.analyticsapi.Repository.PortfolioRepositoryInterface;
import oop.analyticsapi.Repository.StockDailyPriceRepositoryInterface;
import oop.analyticsapi.Repository.UserPortfolioRepositoryInterface;
import oop.analyticsapi.Service.Interface.UserPortfolioServiceInterface;
import oop.analyticsapi.Enums.ActionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserPortfolioService implements UserPortfolioServiceInterface {
    private final PortfolioRepositoryInterface portfolioRepository;
    private final StockDailyPriceRepositoryInterface stockDailyPriceRepository;
    private final UserPortfolioRepositoryInterface userPortfolioRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserPortfolioService.class);

    @Autowired
    public UserPortfolioService(
            PortfolioRepositoryInterface portfolioRepository,
            StockDailyPriceRepositoryInterface stockDailyPriceRepository,
            UserPortfolioRepositoryInterface userPortfolioRepository
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
    public String createNewPortfolio(String userId, String portfolioId, List<Stock> stocks, Timestamp createdAt) throws NumberFormatException {
        //First insert new row in user_portfolio
        String res = "Success";
        int createUserPortfolioEntry = userPortfolioRepository.createUserPortfolioEntry(userId, portfolioId, createdAt);

        userPortfolioRepository.save(UserPortfolioEntity
                        .builder()
                        .portfolioId(portfolioId)
                        .userId(userId)
                        .createdAt(createdAt)
                        .build()
        );

        LocalDateTime ldt = createdAt.toLocalDateTime();
        ldt = ldt.minusDays(1);
        Timestamp oneDayEarlier = Timestamp.valueOf(ldt);

        int portfolioRowChangeCount = insertPortfolioEntries(stocks, portfolioId, oneDayEarlier);

        if (createUserPortfolioEntry == 0
            || portfolioRowChangeCount < stocks.size()) {
            res = "Failed";
        }
        return res;
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
    public String updatePortfolio(String userId, String portfolioId, String action, Stock stock, Timestamp editedAt,
                                  Optional<Integer> addedQuantity) {
        String res = "Success";
        ActionEnum actionEnum = ActionEnum.getActionFromString(action);

        LocalDateTime ldt = editedAt.toLocalDateTime();
        ldt = ldt.minusDays(1);
        Timestamp oneDayEarlier = Timestamp.valueOf(ldt);

        switch (actionEnum) {
            case Add -> {
                //Hack
                List<Stock> stockArray = new ArrayList<>(1);
                stockArray.add(stock);

                int addPortfolioEntry = insertPortfolioEntries(stockArray, portfolioId, oneDayEarlier);

                if (addPortfolioEntry == 0) res = "Failed";
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


    private int insertPortfolioEntries(List<Stock> stocks, String portfolioId, Timestamp createdAt) {
        int portfolioRowChangeCount = 0;
        List<PortfolioEntity> portfolioEntities = new ArrayList<>();

        for (Stock stock : stocks) {
            String symbol = stock.getSymbol();
            //Get price on creation of portfolio
            int quantity = stock.getQuantity();
            Optional<StockDailyPriceEntity> stockPrice = stockDailyPriceRepository.getStockDailyPriceBySymbol(symbol, createdAt);
            if (stockPrice.isPresent()) {
                StockDailyPriceEntity stockData = stockPrice.get();
                try {
                    int close = Integer.parseInt(stockData.getClose());
                    long value = (long) quantity * close;
                    int createPortfolioEntries = portfolioRepository.createPortfolioEntry(portfolioId, quantity, symbol, close, value);
                    portfolioEntities.add(PortfolioEntity.builder()
                            .portfolioId(portfolioId)
                            .averagePrice(close)
                            .symbol(symbol)
                            .quantity(quantity)
                            .value(value)
                            .build());

                    portfolioRowChangeCount += createPortfolioEntries;
                } catch (Exception e) {
                    logger.warn("Integer parse error!");
                    throw e;
                }
            } else {
                logger.warn("No stock price data related to {} found!", symbol);
            }
        }
        portfolioRepository.saveAll(portfolioEntities);
        return portfolioRowChangeCount;
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
