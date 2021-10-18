package com.takado.myportfoliobackend.domain;

import lombok.Data;

@Data
public class AssetDto {
        private final Long id;
        private final String coinId;
        private final String ticker;
        private final String amount;
        private final String valueIn;
}
