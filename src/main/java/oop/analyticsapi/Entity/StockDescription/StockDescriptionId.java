package oop.analyticsapi.Entity.StockDescription;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class StockDescriptionId implements Serializable {
    private String symbol;

    public StockDescriptionId(String symbol) {
        this.symbol = symbol;
    }

}
