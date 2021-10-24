package com.takado.myportfoliobackend.domain.requests;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssetBodyRequest extends BodyRequest {
    private AssetDto assetDto;

    public AssetBodyRequest(AssetDto assetDto, DigitalSignature digitalSignature) {
        super(digitalSignature);
        this.assetDto = assetDto;
    }
}