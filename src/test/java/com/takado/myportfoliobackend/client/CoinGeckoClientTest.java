package com.takado.myportfoliobackend.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoinGeckoClientTest {
    @Autowired
    CoinGeckoClient geckoClient;

    @BeforeAll
    static void beforeAll() {
        System.out.println("\n\n\nstarting tests:\n");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n\n\nend tests.\n");
    }

    @Test
    void testGetPrice() {
        var result = geckoClient.getCoinPrice("cardano", "usd");
        System.out.println("\ntestGetPrice result: " + result);
    }

    @Test
    void testPing() {
        var result = geckoClient.ping();
        System.out.println("\ntestPing result: " + result);
    }

    @Test
    void testGetCoinsPrices() {
        var result = geckoClient.getCoinsPrices("pln", "cardano", "bitcoin");
        System.out.println("\ntestGetCoinsPrices result: \n" + result);
    }

}
