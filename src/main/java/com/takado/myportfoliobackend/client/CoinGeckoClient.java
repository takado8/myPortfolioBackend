package com.takado.myportfoliobackend.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient {
    private final static String apiRoot = "https://api.coingecko.com/api/v3";
    private final RestTemplate restTemplate;

    public String ping() {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot + "/ping")
                .build()
                .encode()
                .toUri();
        var response = restTemplate.getForObject(uri, Map.class);
        return response == null ? null : response.entrySet().toString();
    }

    public Double getCoinPrice(String coinId, String vs_currency) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot + "/simple/price")
                .queryParam("ids", coinId)
                .queryParam("vs_currencies", vs_currency)
                .build()
                .encode()
                .toUri();

        Map<?, ?> response = Optional.ofNullable(restTemplate.getForObject(uri, Map.class)).orElseThrow();
        var coin = response.get(coinId);
        if (coin == null) throw new RuntimeException("coin is null in CoinGeckoClient.getCoinPrice(String, String)");
        Map<?, ?> asset = (Map<?, ?>) coin;
        var price = asset.get(vs_currency);
        if (price == null) throw new RuntimeException("price is null in CoinGeckoClient.getCoinPrice(String, String)");
        return (Double) price;
    }

    public Map<String, Double> getCoinsPrices(String vs_currency, String... coinsIds) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot + "/simple/price")
                .queryParam("ids", String.join(",", coinsIds))
                .queryParam("vs_currencies", vs_currency)
                .build()
                .encode()
                .toUri();
        var response = Optional.ofNullable(restTemplate.getForObject(uri, Map.class)).orElseThrow();

        Map<String, Double> result = new HashMap<>();
        for (var coinId : coinsIds) {
            var coin = (Map<?, ?>) response.get(coinId);
            if (coin != null) {
                Double finalPrice;
                try {
                    finalPrice = (Double) coin.get(vs_currency);
                } catch (ClassCastException e) {
                    Integer price = (Integer) coin.get(vs_currency);
                    finalPrice = price.doubleValue();
                }
                result.put(coinId, finalPrice);
            }
        }
        return result;
    }
}
