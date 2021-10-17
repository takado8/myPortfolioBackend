package com.takado.myportfoliobackend.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient {
    private final static String apiRoot = "https://api.coingecko.com/api/v3";
    private final RestTemplate restTemplate;

    public Map<String, String> ping() {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot + "/ping")
                .build()
                .encode()
                .toUri();
        return Objects.requireNonNull(restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<HashMap<String, String>>() {
                }).getBody());
    }

    public Map<String, HashMap<String, BigDecimal>> getCoinsPrices(String vs_currency, String... coinsIds) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot + "/simple/price")
                .queryParam("ids", String.join(",", coinsIds))
                .queryParam("vs_currencies", vs_currency)
                .build()
                .encode()
                .toUri();
        return Objects.requireNonNull(
                restTemplate.exchange(uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<HashMap<String, HashMap<String, BigDecimal>>>() {
                        }).getBody());
    }
}
