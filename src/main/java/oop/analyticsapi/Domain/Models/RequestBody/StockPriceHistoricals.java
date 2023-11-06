package oop.analyticsapi.Domain.Models.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceHistoricals {
    private String symbol;
    private LocalDate end;
    private LocalDate start;
}
