package oop.analyticsapi.Domain.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {
    private List<PortfolioEntity> stocks;
    private LocalDate createdAt;
    private Double totalValue;
    private Double initialCapital;
    private String description;
}
