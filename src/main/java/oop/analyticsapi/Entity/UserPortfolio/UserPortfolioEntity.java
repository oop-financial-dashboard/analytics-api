package oop.analyticsapi.Entity.UserPortfolio;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserPortfolioId.class)
@Table(name = "user_portfolio")
public class UserPortfolioEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @GeneratedValue
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(name = "initial_capital")
    private Long initialCapital;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;
}


