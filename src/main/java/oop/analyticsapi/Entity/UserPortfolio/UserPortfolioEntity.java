package oop.analyticsapi.Entity.UserPortfolio;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserPortfolioId.class)
@Table(name = "user_portfolio")
public class UserPortfolioEntity {

    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "portfolio_id")
    private String portfolioId;

    @Column(name = "created_at")
    private Date createdAt;
}


