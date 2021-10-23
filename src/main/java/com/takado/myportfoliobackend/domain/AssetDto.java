package com.takado.myportfoliobackend.domain;

import lombok.Data;

@Data
public class AssetDto {
        private final Long id;
        private final Long tickerId;
        private final Long userId;
        private final String amount;
        private final String valueIn;
}
