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
public class PortfolioHistorical {
    private Double totalValue;
    private LocalDate timestamp;
}
