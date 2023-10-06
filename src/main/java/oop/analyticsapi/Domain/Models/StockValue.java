package oop.analyticsapi.Domain.Models;

import java.sql.Timestamp;

public class StockValue extends Stock {
    private Long value;
    private Timestamp calculatedTime;

    public StockValue(String symbol, Integer quantity, Long value, Timestamp calculatedTime) {
        super(symbol, quantity);
        this.value = value;
        this.calculatedTime = calculatedTime;

    }
}
