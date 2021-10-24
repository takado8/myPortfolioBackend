package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.mapper.AssetMapper;
import com.takado.myportfoliobackend.service.AssetDbService;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {
    private final AssetDbService dbService;
    private final AssetMapper mapper;
    private final RequestSignatureService signatureService;

    @GetMapping("")
    public List<AssetDto> getAssets() {
        return mapper.mapToDto(dbService.getAllAssets());
    }

    @PostMapping("{userId}")
    public List<AssetDto> getAssets(@PathVariable Long userId, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        byte[] signature = digitalSignature.getSignature();
        System.out.println("signature: ");
        System.out.println(Arrays.toString(signature));
        if (signature != null && signatureService.verifyDigitalSignature(signature, digitalSignature.getMessage())) {
            return mapper.mapToDto(dbService.getAllAssetsByUserId(userId));
        } else {
            System.out.println("Signature error in getAssets.");
            return Collections.emptyList();
        }
    }

    @PostMapping("")
    public AssetDto createAsset(@RequestBody AssetDto assetDto) {
        return mapper.mapToDto(dbService.saveAsset(mapper.mapToAsset(assetDto)));
    }

    @PutMapping("")
    public AssetDto updateAsset(@RequestBody AssetDto assetDto) {
        return mapper.mapToDto(dbService.updateAsset(assetDto));
    }

    @DeleteMapping("{id}")
    public void deleteAsset(@PathVariable Long id) {
        dbService.deleteAsset(id);
    }
}
