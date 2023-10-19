package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolioEntity, Long> {
   //Read
    @Query(value = """
        SELECT p FROM UserPortfolioEntity p WHERE p.userId = :userId
    """)
    List<UserPortfolioEntity> getAllPortfoliosByUserId(@Param("userId") String userId);

    //Delete
   @Modifying
   @Query(value = """
         DELETE FROM UserPortfolioEntity WHERE userId = :userId AND portfolioId = :portfolioId
         """)
   int deleteUserPortfolioEntry(@Param("userId") String userId, @Param("portfolioId") String portfolioId);

}
