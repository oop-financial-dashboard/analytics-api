package oop.analyticsapi.Domain.ViewModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Domain.Models.Portfolio;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllPortfolios {
    private String userId;
    private Map<String, Portfolio> portfolios;
}
