package oop.analyticsapi.Entity.TemporaryCache;

public class TempCacheId {
    private String portfolioId;
    private String userId;

    public TempCacheId(String userId, String portfolioId) {
        this.portfolioId = portfolioId;
        this.userId = userId;
    }
}
