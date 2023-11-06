package oop.analyticsapi.Service;


import jakarta.transaction.Transactional;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.TemporaryCache.TempCacheEntity;
import oop.analyticsapi.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class PortfolioHistoricalValueTask {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockDailyPriceRepository stockDailyPriceRepository;

    @Autowired
    private PortfolioHistoricalValueRepository portfolioHistoricalValueRepository;

    @Autowired
    private CacheRepository cacheRepository;

    @Value("${spring.datasource.url}")
    private String db_url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;
    private static final String INSERT_HISTORICALS_SQL = "INSERT INTO portfolio_historicals" +
            " (user_id, portfolio_id, symbol, total_value, date) VALUES " +
            " (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;";

    private static final String UPDATE_HISTORICALS_SQL = "UPDATE portfolio_historicals SET total_value = total_value + ?" +
            " WHERE user_id = ? AND portfolio_id = ? AND symbol = ? AND date = ?;";

    private static final Logger logger = LoggerFactory.getLogger(PortfolioHistoricalValueTask.class);

    @Scheduled(cron = "0 0 0,12 * * ?") // Run every day at midnight
    public void calculateCompletePortfolioValue() {
        logger.info("Calculating portfolio's EOD value...");
        List<Object[]> data = portfolioRepository.getAllPortfolioIds();
        //now we call all the stocks for that portfolio
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             PreparedStatement statement = connection.prepareStatement(INSERT_HISTORICALS_SQL)) {

            connection.setAutoCommit(false);

            try {
                for (Object[] id: data) {
                    String userId = (String) id[0];
                    String portfolioId = (String) id[1];
                    List<PortfolioEntity> portfolioEntries = portfolioRepository.getAllStocksInPortfolio(userId, portfolioId);
                    for (PortfolioEntity pe: portfolioEntries) {
                        LocalDate currentDate = LocalDate.now();

                        List<StockDailyPriceEntity> prices = stockDailyPriceRepository
                                .getStockHistoricals(pe.getSymbol(), currentDate, pe.getDateAdded());
                        //now we have all the historicals for that stock, lets insert them
                        for (StockDailyPriceEntity price: prices) {
                            double totalValue = pe.getQuantity() * Double.parseDouble(price.getClose());
                            //get total value for that day
                            statement.setString(1, userId);
                            statement.setString(2, portfolioId);
                            statement.setString(3, pe.getSymbol());
                            statement.setDouble(4, totalValue);
                            statement.setDate(5, Date.valueOf(price.getTimestamp()));
                            System.out.println(statement);
                            statement.addBatch();
                        }
                    }
                    long startTime = System.nanoTime();
                    int[] affectedRecords = statement.executeBatch(); // Execute the batch
                    long estimatedTime = System.nanoTime() - startTime;
                    logger.info("Inserted " + affectedRecords.length + " rows in " +  estimatedTime / 1_000_000+ "ms");
                }
                logger.info("Cronjob for historicals completed!");
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                connection.commit();
            } finally {
                connection.commit();
            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0,12 * * ?") // Run every day at midnight
    public void processHistoricalUpdates() {
        logger.info("Processing historical updates");
        List<TempCacheEntity> data = cacheRepository.getAllUpdates();
        //now we call all the stocks for that portfolio
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             PreparedStatement statement = connection.prepareStatement(UPDATE_HISTORICALS_SQL)) {

            connection.setAutoCommit(false);

            try {
                for (TempCacheEntity id: data) {
                    //This date is the date where the stock was created - so take reference from there
                    LocalDate date = id.getDateAdded();
                    LocalDate currentDate = LocalDate.now();
                    switch (id.getAction()) {
                        case "Remove" -> //Remove all entries from this date onwards
                                portfolioHistoricalValueRepository
                                        .deleteAfterDate(id.getUserId(), id.getPortfolioId(), id.getSymbol(), date);
                        case "Increase" -> {
                            //Update the entries by adding the price
                            //1. Get the additional value on those dates
                            List<StockDailyPriceEntity> historicals = stockDailyPriceRepository
                                    .getStockHistoricals(id.getSymbol(), currentDate, date);
                            for (StockDailyPriceEntity price: historicals) {
                                double value = Double.parseDouble(price.getClose()) * id.getQuantity();
                                statement.setDouble(1, value);
                                statement.setString(2, id.getUserId());
                                statement.setString(3, id.getPortfolioId());
                                statement.setString(4, id.getSymbol());
                                statement.setDate(5, Date.valueOf(price.getTimestamp()));

                                statement.addBatch();
                            }

                        }
                    }
                    long startTime = System.nanoTime();
                    int[] affectedRecords = statement.executeBatch(); // Execute the batch
                    long estimatedTime = System.nanoTime() - startTime;
                    logger.info("Updated " + affectedRecords.length + " rows in " +  estimatedTime / 1_000_000+ "ms");
                }
                logger.info("Processed portfolio updates!");
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                connection.commit();
            } finally {
                //Delete from cache
                cacheRepository.clearCache();
                connection.commit();
            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }
}
