package oop.analyticsapi.Service;

import oop.analyticsapi.Entity.StockListing.StockListing;
import oop.analyticsapi.Repository.StockListingRepository;
import oop.analyticsapi.Service.Interface.StockListingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockListingService implements StockListingInterface {
    private final StockListingRepository stockListingRepository;

    @Autowired
    public StockListingService(StockListingRepository stockListingRepository) {
        this.stockListingRepository = stockListingRepository;
    }

    @Override
    public List<StockListing> getAvailableStocks() {
        return stockListingRepository.getAvailableStocks();
    }

    @Override
    public Optional<StockListing> getStockAvailabilityBySymbol(String symbol) {
        return stockListingRepository.getAvailableStockBySymbol(symbol);
    }
}
