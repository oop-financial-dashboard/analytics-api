package oop.analyticsapi.Entity.StockDailyPrice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(StockDailyPriceId.class)
@Table(name = "stock_daily_price")
//@Table(name = "welp")
public class StockDailyPriceEntity {
    @Id
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    @Id
    private LocalDate timestamp;
}
