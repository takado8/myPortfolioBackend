package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.mapper.AssetMapper;
import com.takado.myportfoliobackend.service.AssetDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {
    private final AssetDbService dbService;
    private final AssetMapper mapper;

    @GetMapping("")
    public List<AssetDto> getAssets() {
        return mapper.mapToDto(dbService.getAllAssets());
    }

    @PostMapping("")
    public AssetDto createAsset(@RequestBody AssetDto assetDto) {
        return mapper.mapToDto(dbService.saveAsset(mapper.mapToAsset(assetDto)));
    }

    @PutMapping("")
    public AssetDto updateAsset(@RequestBody AssetDto assetDto) {
        return mapper.mapToDto(dbService.updateAsset(assetDto));
    }

    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable Long id) {
        dbService.deleteAsset(id);
    }
}
