package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.requests.AssetBodyRequest;
import com.takado.myportfoliobackend.mapper.AssetMapper;
import com.takado.myportfoliobackend.service.AssetDbService;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AssetFacade {
    private final String apiPath = "http://localhost:8081/v1/assets";
    private final AssetDbService dbService;
    private final AssetMapper mapper;
    private final RequestSignatureService signatureService;

    // todo: remove this endpoint.
    public List<AssetDto> getAssets() {
        return mapper.mapToDto(dbService.getAllAssets());
    }

    public List<AssetDto> getAssets(Long userId, DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/" + userId;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                return mapper.mapToDto(dbService.getAllAssetsByUserId(userId));
            } else {
                System.out.println("Signature error in getAssets.");
                return Collections.emptyList();
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
        return Collections.emptyList();
    }

    public AssetDto createAsset(AssetBodyRequest bodyRequest) throws GeneralSecurityException {
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

    public AssetDto updateAsset(AssetBodyRequest bodyRequest) throws GeneralSecurityException {
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

    public void deleteAsset(Long id, DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/delete/" + id;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                dbService.deleteAsset(id);
            } else {
                System.out.println("Signature error in getAssets.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
    }
}
