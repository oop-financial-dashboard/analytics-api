package oop.analyticsapi.Domain.Models.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Domain.Models.Stock;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePortfolioBody {
    private String userId;
    private String portfolioId;
    private String action;
    private List<Stock> stocks;
    private String description;
    private Double initialCapital;
    private LocalDate editedAt;
}
