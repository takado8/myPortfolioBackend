package com.takado.myportfoliobackend.domain;

import lombok.Data;

@Data
public class AssetDto {
        private final Long id;
        private final Long tickerId;
        private final Long userId;
        private final String amount;
        private final String valueIn;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                AssetDto assetDto = (AssetDto) o;

                return tickerId.equals(assetDto.tickerId);
        }

        @Override
        public int hashCode() {
                return tickerId.hashCode();
        }
}
