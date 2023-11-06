package oop.analyticsapi.Service;


import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.TemporaryCache.TempCacheEntity;
import oop.analyticsapi.Repository.CacheRepository;
import oop.analyticsapi.Repository.PortfolioHistoricalValueTransactional;
import oop.analyticsapi.Repository.PortfolioRepository;
import oop.analyticsapi.Repository.StockDailyPriceRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(PortfolioHistoricalValueTask.class);

    @Scheduled(cron = "0 * * * * ?") // Run every day at midnight
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

    @Scheduled(cron = "*/5 * * * * ?") // Run every day at midnight
    public void ProcessHistoricalUpdates() {
        logger.info("Processing historical updates");
        List<TempCacheEntity> data = cacheRepository.getAllUpdates();
        //now we call all the stocks for that portfolio
        try (Connection connection = DriverManager.getConnection(db_url, user, password);
             PreparedStatement statement = connection.prepareStatement(INSERT_HISTORICALS_SQL)) {

            connection.setAutoCommit(false);

            try {
                for (TempCacheEntity id: data) {
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
                logger.info("Processed portfolio updates!");
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
}
