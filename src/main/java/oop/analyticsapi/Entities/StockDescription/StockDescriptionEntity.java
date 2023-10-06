package oop.analyticsapi.Entities.StockDescription;

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
    @Column(name = "symbol")
    private String ticker;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "currency")
    private String currency;
    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "name")
    private String name;

    @Column(name = "description") // Adjust the length as needed
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @Column(name = "fiscal_year_end")
    private String fiscalYearEnd;

    @Column(name = "latest_quarter")
    private String latestQuarter;

    @Column(name = "market_capitalization")
    private String marketCapitalization;

    @Column(name = "ebitda")
    private String ebitda;

    @Column(name = "pe_ratio")
    private String peRatio;

    @Column(name = "peg_ratio")
    private String pegRatio;

    @Column(name = "book_value")
    private String bookValue;

    @Column(name = "dividend_per_share")
    private String dividendPerShare;

    @Column(name = "dividend_yield")
    private String dividendYield;

    @Column(name = "eps")
    private String eps;

    @Column(name = "revenue_per_share_ttm")
    private String revenuePerShareTTM;

    @Column(name = "profit_margin")
    private String profitMargin;

    @Column(name = "operating_margin_ttm")
    private String operatingMarginTTM;

    @Column(name = "return_on_assets_ttm")
    private String returnOnAssetsTTM;

    @Column(name = "return_on_equity_ttm")
    private String returnOnEquityTTM;

    @Column(name = "revenue_ttm")
    private String revenueTTM;

    @Column(name = "gross_profit_ttm")
    private String grossProfitTTM;

    @Column(name = "diluted_eps_ttm")
    private String dilutedEPSTTM;

    @Column(name = "quarterly_earnings_growth_yoy")
    private String quarterlyEarningsGrowthYOY;

    @Column(name = "quarterly_revenue_growth_yoy")
    private String quarterlyRevenueGrowthYOY;

    @Column(name = "analyst_target_price")
    private String analystTargetPrice;

    @Column(name = "trailing_pe")
    private String trailingPE;

    @Column(name = "forward_pe")
    private String forwardPE;

    @Column(name = "price_to_sales_ratio_ttm")
    private String priceToSalesRatioTTM;

    @Column(name = "price_to_book_ratio")
    private String priceToBookRatio;

    @Column(name = "ev_to_revenue")
    private String evToRevenue;

    @Column(name = "ev_to_ebitda")
    private String evToEBITDA;

    @Column(name = "beta")
    private String beta;

    @Column(name = "52_week_high")
    private String _52WeekHigh;

    @Column(name = "52_week_low")
    private String _52WeekLow;

    @Column(name = "50_day_moving_average")
    private String _50DayMovingAverage;

    @Column(name = "200_day_moving_average")
    private String _200DayMovingAverage;

    @Column(name = "shares_outstanding")
    private String sharesOutstanding;

    @Column(name = "dividend_date")
    private String dividendDate;

    @Column(name = "ex_dividend_date")
    private String exDividendDate;
}