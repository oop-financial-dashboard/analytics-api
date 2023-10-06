package oop.analyticsapi.Entities.StockDescription;

import java.io.Serializable;

public class StockDescriptionId implements Serializable {
    private String ticker;

    public StockDescriptionId(String ticker) {
        this.ticker = ticker;
    }

}
