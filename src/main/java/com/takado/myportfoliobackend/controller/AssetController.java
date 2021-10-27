package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.requests.AssetBodyRequest;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.facade.AssetFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {
    private final AssetFacade assetFacade;

    // todo: secure this endpoint.
    @GetMapping("")
    public List<AssetDto> getAssets() {
        return assetFacade.getAssets();
    }

    // todo: secure this endpoint
    @GetMapping("{id}")
    public AssetDto getAsset(@PathVariable Long id){
        return assetFacade.getAsset(id);
    }

    @PostMapping("/ping")
    public String ping(){
        return assetFacade.ping();
    }

    @PostMapping("{userId}")
    public List<AssetDto> getAssets(@PathVariable Long userId, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        return assetFacade.getAssets(userId, digitalSignature);
    }

    @PostMapping("")
    public AssetDto createAsset(@RequestBody AssetBodyRequest bodyRequest) throws GeneralSecurityException {
        return assetFacade.createAsset(bodyRequest);
    }

    @PutMapping("")
    public AssetDto updateAsset(@RequestBody AssetBodyRequest bodyRequest) throws GeneralSecurityException {
        return assetFacade.updateAsset(bodyRequest);
    }

    @PostMapping("/delete/{id}")
    public void deleteAsset(@PathVariable Long id, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        assetFacade.deleteAsset(id, digitalSignature);
    }
}
