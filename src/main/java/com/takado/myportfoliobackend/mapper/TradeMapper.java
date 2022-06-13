package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Trade;
import com.takado.myportfoliobackend.domain.TradeDto;
import com.takado.myportfoliobackend.service.TickerDbService;
import com.takado.myportfoliobackend.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeMapper {
    private final UserDbService userDbService;
    private final TickerDbService tickerDbService;

    public TradeDto mapToDto(Trade trade) {
        return new TradeDto(trade.getId(), trade.getAmount(), trade.getValueIn(), trade.getType(),
                trade.getDateTime(), trade.getTicker().getId(), trade.getUser().getId());
    }

    public Trade mapToTrade(TradeDto tradeDto) {
        return new Trade(tradeDto.getId(), tradeDto.getAmount(), tradeDto.getValueIn(), tradeDto.getType(),
                tradeDto.getDateTime(), tickerDbService.getTicker(tradeDto.getTickerId()),
                userDbService.getUserById(tradeDto.getUserId()));
    }

    public List<TradeDto> mapToDto(List<Trade> trades) {
        return trades.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
