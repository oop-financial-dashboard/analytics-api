package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface StockPriceInterface {
    Optional<StockDailyPriceEntity> getCurrentStockPrice(String symbol, LocalDate timestamp);
    List<StockDailyPriceEntity> getStockHistoricals(String symbol, LocalDate from, Integer days);
}
