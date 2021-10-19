package com.takado.myportfoliobackend.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TickerDto {
    private final Long id;
    private final String ticker;
    private final String coinId;
}
