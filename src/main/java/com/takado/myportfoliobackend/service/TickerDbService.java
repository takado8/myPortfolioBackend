package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.repository.TickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerDbService {
    private final TickerRepository tickerRepository;

    public List<Ticker> getAllTickers() {
        return tickerRepository.findAll();
    }

    public Ticker getTicker(Long id) {
        return tickerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find ticker of id: " + id));
    }

    public Ticker saveTicker(Ticker ticker) {
        return tickerRepository.save(ticker);
    }

    public void deleteTicker(Long tickerId) {
        tickerRepository.deleteById(tickerId);
    }
}
