package oop.analyticsapi.Entity.StockDailyPrice;

import java.io.Serializable;

public class StockDailyPriceId implements Serializable {
    private String symbol;

    public StockDailyPriceId(String symbol) {
        this.symbol = symbol;
    }
}
