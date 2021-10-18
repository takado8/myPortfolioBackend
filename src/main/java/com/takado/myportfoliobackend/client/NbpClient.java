package com.takado.myportfoliobackend.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NbpClient {
    private final static String apiRoot = "http://api.nbp.pl/api/exchangerates/rates/a/usd/";

    private final RestTemplate restTemplate;

    public BigDecimal getExchangeRate() {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiRoot).build().encode().toUri();
        try {
            Map<?, ?> response = restTemplate.getForObject(uri, Map.class);
            if (response == null) return BigDecimal.ONE;
            List<?> list = (List<?>) response.get("rates");
            if (list.size() < 1) return BigDecimal.ONE;
            Map<?,?> map = (Map<?,?>) list.get(0);
            Object value = map.get("mid");
            if (value == null) return BigDecimal.ONE;
            double price;
            try {
                price = (Double) value;
            } catch (ClassCastException e) {
                price = ((Integer) value).doubleValue();
            }
            return BigDecimal.valueOf(price);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return BigDecimal.ONE;
        }
    }
}
