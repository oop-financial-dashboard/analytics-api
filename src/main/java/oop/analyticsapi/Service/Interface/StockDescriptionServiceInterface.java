package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;

import java.util.Optional;

public interface StockDescriptionServiceInterface {
    Optional<StockDescriptionEntity> getStockDescriptionBySymbol(String symbol);
}
