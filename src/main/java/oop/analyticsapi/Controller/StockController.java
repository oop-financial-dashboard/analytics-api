package oop.analyticsapi.Controller;

import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;
import oop.analyticsapi.Service.StockDescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StockController {
    private final StockDescriptionService stockDescriptionService;

    public StockController(StockDescriptionService stockDescriptionService) {
        this.stockDescriptionService = stockDescriptionService;
    }

    @GetMapping("/stock/description/{symbol}")
    public ResponseEntity<Optional<StockDescriptionEntity>> getStockDescription(@PathVariable String symbol) {
        Optional<StockDescriptionEntity> result = stockDescriptionService.getStockDescriptionBySymbol(symbol);
        return ResponseEntity.ok(result);
    }
}
