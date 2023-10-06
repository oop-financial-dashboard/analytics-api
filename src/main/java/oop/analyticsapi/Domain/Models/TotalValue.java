package oop.analyticsapi.Domain.Models;

import java.util.List;

public class TotalValue {
    public TotalValue(List<StockValue> stocks, Long totalValue) {
        this.stocks = stocks;
        this.totalValue = totalValue;
    }

    private List<StockValue> stocks;
    private Long totalValue;
}
