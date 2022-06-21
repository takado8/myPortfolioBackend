package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.TradeDto;
import com.takado.myportfoliobackend.domain.requests.TradeBodyRequest;
import com.takado.myportfoliobackend.facade.TradeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/v1/trades")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TradeController {
    private final TradeFacade facade;

    @PostMapping(path = "{userId}/{tickerCoinId}")
    public List<TradeDto> getTrades(@PathVariable Long userId, @PathVariable String tickerCoinId,
                                    @RequestBody DigitalSignature signature) throws GeneralSecurityException {
        return facade.getTrades(userId, tickerCoinId, signature);
    }

    @PostMapping(path = "")
    public TradeDto saveTrade(@RequestBody TradeBodyRequest tradeBodyRequest) throws GeneralSecurityException {
        return facade.saveTrade(tradeBodyRequest);
    }
}
