package oop.analyticsapi.Controller;

import oop.analyticsapi.Domain.Models.RequestBody.StockPrice;
import oop.analyticsapi.Domain.Models.RequestBody.StockPriceHistoricals;
import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;
import oop.analyticsapi.Entity.StockListing.StockListing;
import oop.analyticsapi.Service.StockDescriptionService;
import oop.analyticsapi.Service.StockListingService;
import oop.analyticsapi.Service.StockPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StockController {
    private final StockDescriptionService stockDescriptionService;
    private final StockPriceService stockPriceService;
    private final StockListingService stockListingService;

    public StockController(
            StockDescriptionService stockDescriptionService,
            StockPriceService stockPriceService,
            StockListingService stockListingService
    ) {
        this.stockDescriptionService = stockDescriptionService;
        this.stockPriceService = stockPriceService;
        this.stockListingService = stockListingService;
    }

    @GetMapping("/stock/description/{symbol}")
    public ResponseEntity<Optional<StockDescriptionEntity>> getStockDescription(@PathVariable String symbol) {
        Optional<StockDescriptionEntity> result = stockDescriptionService.getStockDescriptionBySymbol(symbol);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/stock/price")
    public ResponseEntity<Optional<StockDailyPriceEntity>> getStockPriceBySymbol(@RequestBody StockPrice stockPrice) {
        Optional<StockDailyPriceEntity> result = stockPriceService.getCurrentStockPrice(stockPrice.getSymbol(), stockPrice.getTimestamp());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/stock/historicals")
    public ResponseEntity<List<StockDailyPriceEntity>> getStockPriceHistoricals(@RequestBody StockPriceHistoricals stockPrice) {
        List<StockDailyPriceEntity> result = stockPriceService.getStockHistoricals(stockPrice.getSymbol(), stockPrice.getFrom(), stockPrice.getDays());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stock/available-stocks")
    public ResponseEntity<List<StockListing>> getAvailableStocks() {
        return ResponseEntity.ok(stockListingService.getAvailableStocks());
    }
}
