package oop.analyticsapi.Service;


import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Repository.PortfolioHistoricalValueTransactional;
import oop.analyticsapi.Repository.PortfolioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PortfolioHistoricalValueTask {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private PortfolioHistoricalValueTransactional portfolioHistoricalValueTransactional;

    private static final Logger logger = LoggerFactory.getLogger(PortfolioHistoricalValueTask.class);

    @Scheduled(cron = "0 0 * * * ?") // Run every day at midnight
    public void calculatePortfolioValue() {
        logger.info("Calculating portfolio's EOD value...");
        List<Object[]> data = portfolioRepository.getAllPortfolioIds();
        System.out.println(data);
        for (Object[] id: data) {
            double totalValue = 0.0;
            String userId = (String) id[0];
            String portfolioId = (String) id[1];
            List<PortfolioEntity> portfolioEntries = portfolioRepository.getAllStocksInPortfolio(userId, portfolioId);
            for (PortfolioEntity pe: portfolioEntries) {
                totalValue += pe.getValue();
            }
            LocalDate currentDate = LocalDate.now();
            try {
                portfolioHistoricalValueTransactional.insertDailyPortfolioValue(userId , portfolioId, totalValue, currentDate);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("Inserted all portfolio historicals!");
    }
}
