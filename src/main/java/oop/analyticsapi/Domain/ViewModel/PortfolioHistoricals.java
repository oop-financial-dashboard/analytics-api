package oop.analyticsapi.Domain.ViewModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Domain.Models.PortfolioHistorical;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PortfolioHistoricals {
    private Map<String, List<PortfolioHistorical>> data;
}
