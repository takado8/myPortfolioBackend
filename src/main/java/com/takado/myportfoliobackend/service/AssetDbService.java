package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetDbService {
    private final AssetRepository repository;

    public List<Asset> getAllAssets() {
        return repository.findAll();
    }

    public Asset saveAsset(Asset asset) {
        return repository.save(asset);
    }

    public void deleteAsset(Long assetId) {
        repository.deleteById(assetId);
    }

    public Asset updateAsset(AssetDto assetDto) {
        Asset fromDb = repository.findById(assetDto.getId()).orElseThrow( () ->
                new RuntimeException("asset with id: " + assetDto.getId() + " does not exist"));
        fromDb.setAmount(assetDto.getAmount());
        fromDb.setValueIn(assetDto.getValueIn());
        return repository.save(fromDb);
    }

}
