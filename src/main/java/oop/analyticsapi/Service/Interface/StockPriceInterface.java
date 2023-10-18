package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;

import java.time.LocalDate;
import java.util.Optional;


public interface StockPriceInterface {
    Optional<StockDailyPriceEntity> getCurrentStockPrice(String symbol, LocalDate timestamp);
}
