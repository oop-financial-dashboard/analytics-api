package oop.analyticsapi.Domain.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PortfolioAggregatedHistoricals {

    private String userId;

    private String portfolioId;

    private double value;

    private LocalDate date;
}
