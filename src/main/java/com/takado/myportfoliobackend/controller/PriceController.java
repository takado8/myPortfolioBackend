package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/prices")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return priceService.ping();
    }

    @GetMapping("/{vs_currency}/{coinsIds}")
    public Map<String, HashMap<String, BigDecimal>> getCoinsPrices(@PathVariable String vs_currency,
                                                                   @PathVariable String... coinsIds) {
        return priceService.getCoinsPrices(vs_currency, coinsIds);
    }

    @GetMapping("/exchangeRate")
    public BigDecimal getExchangeRate() {
        return priceService.getExchangeRate();
    }
}
