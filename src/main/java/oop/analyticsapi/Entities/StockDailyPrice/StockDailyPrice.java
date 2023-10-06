package oop.analyticsapi.Entities.StockDailyPrice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Entities.StockDescription.StockDescriptionId;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(StockDailyPriceId.class)
@Table(name = "stock_daily_price")
public class StockDailyPrice {
    @Id
    @Column(name = "symbol")
    private String ticker;

    @Column(name = "date")
    private Timestamp date;

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
