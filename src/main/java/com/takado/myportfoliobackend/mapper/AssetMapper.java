package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.service.TickerDbService;
import com.takado.myportfoliobackend.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetMapper {
    private final TickerDbService tickerDbService;
    private final UserDbService userDbService;

    public AssetDto mapToDto(Asset asset) {
        return new AssetDto(asset.getId(), asset.getTicker().getId(),
                asset.getUser().getId(), asset.getAmount(), asset.getValueIn());
    }

    public Asset mapToAsset(AssetDto assetDto) {
        Ticker ticker = tickerDbService.getTicker(assetDto.getTickerId());
        User user = userDbService.getUserById(assetDto.getUserId());
        return new Asset(ticker, user, assetDto.getAmount(), assetDto.getValueIn());
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
