package oop.analyticsapi.Entity.StockDescription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(StockDescriptionId.class)
@Table(name = "stock_description")
public class StockDescriptionEntity {
    @Id
    private String symbol;
    private String exchange;
    private String currency;
    private String assetType;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String country;
    private String sector;
    private String industry;
    private String fiscalYearEnd;
    private String latestQuarter;
    private String marketCapitalization;
    private String ebitda;
    private String peRatio;
    private String pegRatio;
    private String bookValue;
    private String dividendPerShare;
    private String dividendYield;
    private String eps;
    private String revenuePerShareTTM;
    private String profitMargin;
    private String operatingMarginTTM;
    private String returnOnAssetsTTM;
    private String returnOnEquityTTM;
    private String revenueTTM;
    private String grossProfitTTM;
    private String dilutedEPSTTM;
    private String quarterlyEarningsGrowthYOY;
    private String quarterlyRevenueGrowthYOY;
    private String analystTargetPrice;
    private String trailingPE;
    private String forwardPE;
    private String priceToSalesRatioTTM;
    private String priceToBookRatio;
    private String evToRevenue;
    private String evToEBITDA;
    private String beta;
    private String _52WeekHigh;
    private String _52WeekLow;
    private String _50DayMovingAverage;
    private String _200DayMovingAverage;
    private String sharesOutstanding;
    private String dividendDate;
    private String exDividendDate;
}
