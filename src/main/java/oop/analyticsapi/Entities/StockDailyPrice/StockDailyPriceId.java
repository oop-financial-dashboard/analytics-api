package oop.analyticsapi.Entities.StockDailyPrice;

import java.io.Serializable;

public class StockDailyPriceId implements Serializable {
    private String ticker;

    public StockDailyPriceId(String ticker) {
        this.ticker = ticker;
    }
}
