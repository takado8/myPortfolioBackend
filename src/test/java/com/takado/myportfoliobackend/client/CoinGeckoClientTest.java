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
    void testPing() {
        var result = geckoClient.ping();
        System.out.println("\ntestPing result: " + result + "\n");
        assertNotNull(result);
        assertTrue(result.containsKey("gecko_says"));
    }

    @Test
    void testGetCoinsPrices() {
        var result = geckoClient.getCoinsPrices(
                "pln", "cardano", "bitcoin");
        System.out.println("\ntestGetCoinsPrices result: \n" + result);
        assertNotNull(result);
        System.out.println("\n\nend test.\n\n");
    }

    @Test
    void scratchbook(){
    }
}
