package oop.analyticsapi.Domain.Models.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Domain.Models.Stock;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioBody {
    private String userId;
    private String portfolioId;
    private List<Stock> stocks;
    private Timestamp createdAt;
}
