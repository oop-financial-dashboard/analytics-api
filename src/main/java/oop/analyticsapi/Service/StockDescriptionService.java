package oop.analyticsapi.Service;

import lombok.Data;

import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;
import oop.analyticsapi.Repository.StockDescriptionRepository;
import oop.analyticsapi.Service.Interface.StockDescriptionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class StockDescriptionService implements StockDescriptionServiceInterface {
    private final StockDescriptionRepository stockDescriptionRepository;

    @Autowired
    public StockDescriptionService(StockDescriptionRepository stockDescriptionRepository) {
        this.stockDescriptionRepository = stockDescriptionRepository;
    }

    @Override
    public Optional<StockDescriptionEntity> getStockDescriptionBySymbol(String symbol) {
        return stockDescriptionRepository.getStockDescriptionBySymbol(symbol);
    }
}
