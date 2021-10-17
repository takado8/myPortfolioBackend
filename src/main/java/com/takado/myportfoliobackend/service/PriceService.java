package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.client.CoinGeckoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final CoinGeckoClient geckoClient;

    public Map<String, HashMap<String, BigDecimal>> getCoinsPrices(String vs_currency, String... coinsIds) {
        return geckoClient.getCoinsPrices(vs_currency, coinsIds);
    }

    public Map<String, String> ping() {
        return geckoClient.ping();
    }
}
