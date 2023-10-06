package oop.analyticsapi.Entity.StockDescription;

import java.io.Serializable;

public class StockDescriptionId implements Serializable {
    private String ticker;

    public StockDescriptionId(String ticker) {
        this.ticker = ticker;
    }

}
