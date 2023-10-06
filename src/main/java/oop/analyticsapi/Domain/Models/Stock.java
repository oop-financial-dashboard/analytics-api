package oop.analyticsapi.Domain.Models;

import java.util.Optional;

public class Stock {
    private String symbol;
    private Integer quantity;

    public Stock(String symbol, Integer quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }
}
