package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.TradeDto;
import com.takado.myportfoliobackend.domain.requests.TradeBodyRequest;
import com.takado.myportfoliobackend.mapper.TradeMapper;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import com.takado.myportfoliobackend.service.TradeDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeFacade {
    private final String apiPath = "http://localhost:8081/v1/trades/";
    private final TradeDbService dbService;
    private final RequestSignatureService signatureService;
    private final TradeMapper mapper;


    public List<TradeDto> getTrades(Long userId, Long tickerId, DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + userId + tickerId;
        String signedPath = digitalSignature.getMessage();
        if (signatureService.validateSignature(receivedDataPath, signedPath, digitalSignature)) {
            var trades = dbService.getAllTradesByUserIdAndTickerId(userId, tickerId);
            return mapper.mapToDto(trades);
        }
        return Collections.emptyList();
    }

    public TradeDto saveTrade(TradeBodyRequest tradeBodyRequest) throws GeneralSecurityException {
        DigitalSignature digitalSignature = tradeBodyRequest.getDigitalSignature();
        TradeDto tradeDto = tradeBodyRequest.getTradeDto();
        String tradeDtoString = tradeDto.toString();
        String signatureTradeDtoString = digitalSignature.getMessage();
        if (signatureService.validateSignature(tradeDtoString, signatureTradeDtoString, digitalSignature)) {
            return mapper.mapToDto(dbService.saveTrade(mapper.mapToTrade(tradeDto)));
        }
        return new TradeDto(null, null, null, null, null, null, null);
    }

}
