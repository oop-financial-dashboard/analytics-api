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
public class StockDailyPriceEntity {
    @Id
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "open")
    private String open;

    @Column(name = "high")
    private String high;

    @Column(name = "low")
    private String low;

    @Column(name = "close")
    private String close;

    @Column(name = "volume")
    private String volume;
}
