package oop.analyticsapi.Repository;



import oop.analyticsapi.Entity.TemporaryCache.TempCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CacheRepository extends JpaRepository<TempCacheEntity, Long> {
    @Query(value = """
           SELECT c FROM TempCacheEntity c
       """)
    List<TempCacheEntity> getAllUpdates();

    @Modifying
    @Query(value = """
           DELETE FROM TempCacheEntity c WHERE 1=1
       """)
    void clearCache();
}
