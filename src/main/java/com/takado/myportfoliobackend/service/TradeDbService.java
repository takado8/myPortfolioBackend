package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Trade;
import com.takado.myportfoliobackend.repository.TradeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeDbService {
    private final TradeRepository repository;

    public List<Trade> getAllTrades() {
        return repository.findAll();
    }

    public List<Trade> getAllTradesByUserIdAndTickerId(Long userIid, Long tickerId) {
        return repository.findAllByUserIdAndTickerId(userIid, tickerId);
    }

    public Trade saveTrade(Trade trade) {
        return repository.save(trade);
    }

    public void deleteTrade(Long tradeId) {
        repository.deleteById(tradeId);
    }
}
