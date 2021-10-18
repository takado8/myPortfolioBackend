package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.domain.AssetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetMapper {
    public AssetDto mapToDto(Asset asset) {
        return new AssetDto(asset.getId(), asset.getCoinId(), asset.getTicker(), asset.getAmount(), asset.getValueIn());
    }

    public Asset mapToAsset(AssetDto assetDto) {
        return new Asset(assetDto.getCoinId(), assetDto.getTicker(), assetDto.getAmount(), assetDto.getValueIn());
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
