package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.TickerDto;
import com.takado.myportfoliobackend.mapper.TickerMapper;
import com.takado.myportfoliobackend.service.TickerDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/tickers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TickerController {
    private final TickerDbService dbService;
    private final TickerMapper mapper;

    @GetMapping("")
    public List<TickerDto> getAllTickers() {
        return mapper.mapToDtoList(dbService.getAllTickers());
    }

    @PostMapping("/{ticker}/{coinId}")
    public TickerDto createTicker(@PathVariable @NotNull String ticker, @PathVariable @NotNull String coinId) {
        return mapper.mapToDto(dbService.saveTicker(mapper.mapToTicker(new TickerDto(null, ticker, coinId))));
    }

    @DeleteMapping("{id}")
    public void deleteTicker(@PathVariable @NotNull Long id){
        dbService.deleteTicker(id);
    }
}
