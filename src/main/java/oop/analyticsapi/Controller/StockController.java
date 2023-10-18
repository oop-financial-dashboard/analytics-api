package oop.analyticsapi.Controller;

import oop.analyticsapi.Domain.Models.RequestBody.StockPrice;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;
import oop.analyticsapi.Service.StockDescriptionService;
import oop.analyticsapi.Service.StockPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StockController {
    private final StockDescriptionService stockDescriptionService;
    private final StockPriceService stockPriceService;

    public StockController(StockDescriptionService stockDescriptionService, StockPriceService stockPriceService) {
        this.stockDescriptionService = stockDescriptionService;
        this.stockPriceService = stockPriceService;

    }

    @GetMapping("/stock/description/{symbol}")
    public ResponseEntity<Optional<StockDescriptionEntity>> getStockDescription(@PathVariable String symbol) {
        Optional<StockDescriptionEntity> result = stockDescriptionService.getStockDescriptionBySymbol(symbol);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/stock/price")
    public ResponseEntity<Optional<StockDailyPriceEntity>> getStockDescription(@RequestBody StockPrice stockPrice) {
        Optional<StockDailyPriceEntity> result = stockPriceService.getCurrentStockPrice(stockPrice.getSymbol(), stockPrice.getTimestamp());
        return ResponseEntity.ok(result);
    }
}
