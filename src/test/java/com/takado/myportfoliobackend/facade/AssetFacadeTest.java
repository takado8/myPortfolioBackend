package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.requests.AssetBodyRequest;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AssetFacadeTest {
    @Autowired
    AssetFacade assetFacade;
    @Mock
    RequestSignatureService signatureService;

    AssetDto assetInDb;

    @BeforeEach
    void beforeEach() throws GeneralSecurityException {
        assetFacade.setSignatureService(signatureService);
        AssetDto assetDto = new AssetDto(null, 190L, 193L, "0.5", "400");
        var signature = new DigitalSignature(new byte[1],
                "AssetDto(id=null, tickerId=190, userId=193, amount=0.5, valueIn=400)");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        assetInDb = assetFacade.createAsset(bodyRequest);
    }

    @AfterEach
    void afterEach() {
        try {
            // given
            var assetInDbId = assetInDb.getId();
            assetFacade.setSignatureService(signatureService);
            var signature = new DigitalSignature(new byte[1],
                    "http://localhost:8081/v1/assets/delete/"+assetInDbId);
            when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
            //when
            assetFacade.deleteAsset(assetInDbId, signature);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createAsset() throws GeneralSecurityException {
        // given
        assetFacade.setSignatureService(signatureService);
        AssetDto assetDto = new AssetDto(null, 190L, 193L, "0.5", "400");
        var signature = new DigitalSignature(new byte[1],
                "AssetDto(id=null, tickerId=190, userId=193, amount=0.5, valueIn=400)");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var result = assetFacade.createAsset(bodyRequest);
        //then
        assertEquals(assetDto, result);
    }

    @Test
    void getAssets() {
        //when
        var result = assetFacade.getAssets();
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.contains(assetInDb));
    }

    @Test
    void ping() {
        // when
        var result = assetFacade.ping();
        //then
        assertEquals("pong", result);
    }

    @Test
    void testGetAssets() throws GeneralSecurityException {
        //given
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/assets/193");
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        assetFacade.setSignatureService(signatureService);
        //when
        var assets = assetFacade.getAssets(193L, signature);
        //then
        assertTrue(assets.size() > 0);
    }

    @Test
    void updateAsset() throws GeneralSecurityException {
        // given
        assetFacade.setSignatureService(signatureService);
        var assetInDbId = assetInDb.getId();
        AssetDto assetDto = new AssetDto(assetInDbId, 190L, 193L, "0.5", "400");
        var signature = new DigitalSignature(new byte[1],
                "AssetDto(id=" + assetInDbId + ", tickerId=190, userId=193, amount=0.5, valueIn=400)");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var result = assetFacade.updateAsset(bodyRequest);
        //then
        assertEquals(assetDto, result);

    }

    @Test
    void getAsset() throws GeneralSecurityException {
        //given
        var assetInDbId = assetInDb.getId();
        assetFacade.setSignatureService(signatureService);
        AssetDto assetDto = new AssetDto(assetInDbId, 190L, 193L, "0.5", "400");
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/assets/delete/" + assetInDbId);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var asset = assetFacade.getAsset(assetInDbId);
        //then
        assertEquals(assetDto, asset);
    }

    @Test
    void deleteAsset() throws GeneralSecurityException {
        // given
        var assetInDbId = assetInDb.getId();
        assetFacade.setSignatureService(signatureService);
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/assets/delete/"+assetInDbId);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        assetFacade.deleteAsset(assetInDbId, signature);
        //then
        var assetShouldNotExist = assetFacade.getAsset(assetInDbId);
        assertNull(assetShouldNotExist);
    }

}