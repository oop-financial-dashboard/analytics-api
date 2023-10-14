package oop.analyticsapi.Domain.Models;


import java.time.LocalDate;

public class StockValue extends Stock {
    private Long value;
    private LocalDate calculatedTime;

    public StockValue(String symbol, Integer quantity, Long value, LocalDate calculatedTime) {
        super(symbol, quantity);
        this.value = value;
        this.calculatedTime = calculatedTime;

    }
}
