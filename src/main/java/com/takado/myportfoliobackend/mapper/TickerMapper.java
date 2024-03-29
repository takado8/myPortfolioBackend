package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.TickerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TickerMapper {
    public Ticker mapToTicker(TickerDto tickerDto) {
        return new Ticker(tickerDto.getId(), tickerDto.getTicker(), tickerDto.getCoinId(), null, null);
    }

    public TickerDto mapToDto(Ticker ticker) {
        return new TickerDto(ticker.getId(), ticker.getTicker(), ticker.getCoinId());
    }

    public List<TickerDto> mapToDtoList(List<Ticker> tickers) {
        return tickers.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
