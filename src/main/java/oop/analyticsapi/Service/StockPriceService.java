package oop.analyticsapi.Service;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Repository.StockDailyPriceRepository;
import oop.analyticsapi.Service.Interface.StockPriceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockPriceService implements StockPriceInterface {
    private final StockDailyPriceRepository stockDailyPriceRepository;

    @Autowired
    public StockPriceService(StockDailyPriceRepository stockDailyPriceRepository) {
        this.stockDailyPriceRepository = stockDailyPriceRepository;
    }

    @Override
    public Optional<StockDailyPriceEntity> getCurrentStockPrice(String symbol, LocalDate timestamp) {
        LocalDate today = LocalDate.now();
        if (today.equals(timestamp)) {
            //minus 1 day bc we dh current day data
//            LocalDate oneDayEarlier = timestamp.minusDays(1);
            return stockDailyPriceRepository.getStockDailyPriceBySymbol(symbol, timestamp);
        }
        return stockDailyPriceRepository.getStockDailyPriceBySymbol(symbol, timestamp);
    }

    @Override
    public List<StockDailyPriceEntity> getStockHistoricals(String symbol, LocalDate end, LocalDate start) {
        return stockDailyPriceRepository.getStockHistoricals(symbol, end, start);
    }
}
