package oop.analyticsapi.Entity.PortfolioHistoricals;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.analyticsapi.Entity.Portfolio.PortfolioId;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PortfolioValueId.class)
@Table(name = "portfolio_historicals")
public class PortfolioValue {
    @Id
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(name = "total_value")
    private double value;

    @Id
    @Column(name = "date")
    private LocalDate date;
}

