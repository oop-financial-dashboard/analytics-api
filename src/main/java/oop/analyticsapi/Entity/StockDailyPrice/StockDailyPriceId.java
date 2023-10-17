package oop.analyticsapi.Entity.StockDailyPrice;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
public class StockDailyPriceId implements Serializable {
    private String symbol;
    private LocalDate timestamp;
}
