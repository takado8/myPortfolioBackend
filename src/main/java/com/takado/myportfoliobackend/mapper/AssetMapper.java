package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.TickerDto;
import com.takado.myportfoliobackend.service.TickerDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetMapper {
    private final TickerDbService tickerDbService;

    public AssetDto mapToDto(Asset asset) {
        return new AssetDto(asset.getId(), asset.getTicker().getId(), asset.getAmount(), asset.getValueIn());
    }

    public Asset mapToAsset(AssetDto assetDto) {
        Ticker ticker = tickerDbService.getTicker(assetDto.getTickerId());
        return new Asset(ticker, assetDto.getAmount(), assetDto.getValueIn());
    }

    public List<AssetDto> mapToDto(List<Asset> assets) {
        return assets.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Asset> mapToAsset(List<AssetDto> assetDtos) {
        return assetDtos.stream()
                .map(this::mapToAsset)
                .collect(Collectors.toList());
    }
}
