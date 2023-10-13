package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.sql.Timestamp;
import java.util.List;

public interface UserPortfolioRepositoryInterface extends JpaRepository<UserPortfolioEntity, Long> {
   //Read
    @Query(value = """
        SELECT * FROM UserPortfolioEntity p WHERE p.userId = :userId
    """, nativeQuery = true)
    List<UserPortfolioEntity> getAllPortfoliosByUserId(String userId);

    //Create
    @Modifying
    @Query(value = """
             INSERT INTO UserPortfolioEntity (userId, portfolioId, createdAt)
                    VALUES (:userId, :portfolioId, :createdAt)
            """, nativeQuery = true)
    int createUserPortfolioEntry(String userId, String portfolioId, Timestamp createdAt);

    //Delete
   @Modifying
   @Query(value = """
         DELETE FROM UserPortfolioEntity WHERE userId = :userId AND portfolioId = :portfolioId
         """, nativeQuery = true)
   int deleteUserPortfolioEntry(String userId, String portfolioId);

}
