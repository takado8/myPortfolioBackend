package com.takado.myportfoliobackend.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CoinGeckoClientTest {
    @Autowired
    CoinGeckoClient geckoClient;

    @Test
    void ping() {
        var result = geckoClient.ping();
        assertNotNull(result);
        assertTrue(result.containsKey("gecko_says"));
    }

    @Test
    void getCoinsPrices() {
        var result = geckoClient.getCoinsPrices(
                "pln", "cardano", "bitcoin");
        assertNotNull(result);
    }
}
