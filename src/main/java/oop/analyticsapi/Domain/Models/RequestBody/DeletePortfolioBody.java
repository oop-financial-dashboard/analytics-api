package oop.analyticsapi.Domain.Models.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePortfolioBody {
    private String userId;
    private String portfolioId;
}
