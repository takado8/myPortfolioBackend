package com.takado.myportfoliobackend.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PriceCacheService {
    private final static int PRICES_FETCH_TIME_DELAY = 10;
    private final Map<String, HashMap<String, BigDecimal>> cachedPrices = new HashMap<>();
    private long pricesLastFetchTime = 0;

    @Nullable
    public Map<String, HashMap<String, BigDecimal>> getCoinsPrices(String... coinsIds){
        if (isTimeToFetchFreshData()) return null;

        Map<String, HashMap<String, BigDecimal>> result = new HashMap<>();
        for(var coinId : coinsIds){
            if (cachedPrices.containsKey(coinId)){
                result.put(coinId, cachedPrices.get(coinId));
            }
            else {
                return null;
            }
        }
        return result;
    }

    public void storeCoinsPrices(Map<String, HashMap<String, BigDecimal>> coinsPrices){
        for (var entry : coinsPrices.entrySet()){
            String coinId = entry.getKey();
            cachedPrices.put(coinId, entry.getValue());
        }
        pricesLastFetchTime = currentTimeSeconds();
    }

    private boolean isTimeToFetchFreshData() {
        return currentTimeSeconds() - pricesLastFetchTime > PRICES_FETCH_TIME_DELAY;
    }

    private long currentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

}
