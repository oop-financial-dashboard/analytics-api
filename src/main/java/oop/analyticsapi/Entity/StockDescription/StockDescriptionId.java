package oop.analyticsapi.Entity.StockDescription;

import java.io.Serializable;

public class StockDescriptionId implements Serializable {
    private String symbol;

    public StockDescriptionId(String symbol) {
        this.symbol = symbol;
    }

}
