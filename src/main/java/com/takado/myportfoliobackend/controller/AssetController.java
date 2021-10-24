package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.requests.AssetBodyRequest;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.mapper.AssetMapper;
import com.takado.myportfoliobackend.service.AssetDbService;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {
    private final String apiPath = "http://localhost:8081/v1/assets";
    private final AssetDbService dbService;
    private final AssetMapper mapper;
    private final RequestSignatureService signatureService;

    // todo: remove this endpoint.
    @GetMapping("")
    public List<AssetDto> getAssets() {
        return mapper.mapToDto(dbService.getAllAssets());
    }

    @PostMapping("{userId}")
    public List<AssetDto> getAssets(@PathVariable Long userId, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/" + userId;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                return mapper.mapToDto(dbService.getAllAssetsByUserId(userId));
            } else {
                System.out.println("Signature error in getAssets.");
                return Collections.emptyList();
            }
        }else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
        return Collections.emptyList();
    }

    @PostMapping("")
    public AssetDto createAsset(@RequestBody AssetBodyRequest bodyRequest) throws GeneralSecurityException {
        DigitalSignature digitalSignature = bodyRequest.getDigitalSignature();
        AssetDto assetDto = bodyRequest.getAssetDto();
        String assetDtoString = assetDto.toString();
        String signatureAssetDtoString = digitalSignature.getMessage();
        if (assetDtoString.equals(signatureAssetDtoString)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                return mapper.mapToDto(dbService.saveAsset(mapper.mapToAsset(assetDto)));
            } else {
                System.out.println("Signature verification failed.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + assetDtoString
                    + "\nsigned: " + signatureAssetDtoString);
        }
        return new AssetDto(null, null, null, null, null);
    }

    @PutMapping("")
    public AssetDto updateAsset(@RequestBody AssetBodyRequest bodyRequest) throws GeneralSecurityException {
        DigitalSignature digitalSignature = bodyRequest.getDigitalSignature();
        AssetDto assetDto = bodyRequest.getAssetDto();
        String assetDtoString = assetDto.toString();
        String signatureAssetDtoString = digitalSignature.getMessage();
        if (assetDtoString.equals(signatureAssetDtoString)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                return mapper.mapToDto(dbService.updateAsset(assetDto));
            } else {
                System.out.println("Signature verification failed.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + assetDtoString
                    + "\nsigned: " + signatureAssetDtoString);
        }
        return new AssetDto(null, null, null, null, null);
    }

    @PostMapping("/delete/{id}")
    public void deleteAsset(@PathVariable Long id, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/delete/" + id;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                dbService.deleteAsset(id);
            } else {
                System.out.println("Signature error in getAssets.");
            }
        }else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
    }
}
