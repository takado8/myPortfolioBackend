package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {

    @GetMapping("")
    public List<AssetDto> getAssets() {
        return List.of(new AssetDto("ADA", "1000", "300"),
                new AssetDto("BTC", "0.1", "1000"),
                new AssetDto("ETH", "2", "2000"));
    }

    @PostMapping(value = "")
    public AssetDto createAsset(@RequestBody AssetDto assetDto) {
        System.out.println(assetDto);
        return assetDto;
    }

    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable Long id) {
        System.out.println("\nDeleting of " + id + " requested\n");
    }
}
