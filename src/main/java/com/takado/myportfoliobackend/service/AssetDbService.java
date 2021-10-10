package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetDbService {
    private final AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

}
