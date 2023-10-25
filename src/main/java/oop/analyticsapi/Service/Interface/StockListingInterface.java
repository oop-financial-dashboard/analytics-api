package oop.analyticsapi.Service.Interface;

import oop.analyticsapi.Entity.StockListing.StockListing;

import java.util.List;
import java.util.Optional;

public interface StockListingInterface {
    List<StockListing> getAvailableStocks();
    Optional<StockListing> getStockAvailabilityBySymbol(String symbol);
}
