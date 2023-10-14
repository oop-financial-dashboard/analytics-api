package oop.analyticsapi.Repository;

import jakarta.transaction.Transactional;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Timestamp;
import java.util.List;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolioEntity, Long> {
   //Read
    @Query(value = """
        SELECT * FROM UserPortfolioEntity p WHERE p.userId = :userId
    """, nativeQuery = true)
    List<UserPortfolioEntity> getAllPortfoliosByUserId(@Param("userId") String userId);

    //Create
    @Modifying
    @Query(value = """
             INSERT INTO UserPortfolioEntity (userId, portfolioId, createdAt)
                    VALUES (:userId, :portfolioId, :createdAt)
            """, nativeQuery = true)
    int createUserPortfolioEntry(@Param("userId") String userId, @Param("portfolioId") String portfolioId,
                                 @Param("createdAt")Timestamp createdAt);

    //Delete
   @Modifying
   @Query(value = """
         DELETE FROM UserPortfolioEntity WHERE userId = :userId AND portfolioId = :portfolioId
         """)
   int deleteUserPortfolioEntry(@Param("userId") String userId, @Param("portfolioId") String portfolioId);

}
