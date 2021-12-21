package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.domain.requests.AssetBodyRequest;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import com.takado.myportfoliobackend.service.TickerDbService;
import com.takado.myportfoliobackend.service.UserDbService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AssetFacadeTest {
    @Autowired
    AssetFacade assetFacade;
    @Mock
    RequestSignatureService signatureService;

    AssetDto assetInDb;

    static Long tickerId;
    static Long userId;
    static boolean removeTickerAfter = false;
    static boolean removeUserAfter = false;

    @BeforeAll
    static void beforeAll(@Autowired TickerDbService tickerDbService, @Autowired UserDbService userDbService) {
        var tickersFromDb = tickerDbService.getAllTickers();
        if (tickersFromDb.size() > 0) {
            AssetFacadeTest.tickerId = tickersFromDb.get(0).getId();
        } else {
            tickerDbService.saveTicker(new Ticker(1L, "BTC", "bitcoin", Collections.emptyList()));
            var newTickerFromDb = tickerDbService.getAllTickers().get(0);
            if (newTickerFromDb == null) {
                throw new NullPointerException("Cannot create new ticker for tests.");
            } else {
                AssetFacadeTest.removeTickerAfter = true;
                AssetFacadeTest.tickerId = newTickerFromDb.getId();
            }
        }
        var usersFromDb = userDbService.getAllUsers();
        if (usersFromDb.size() > 0) {
            AssetFacadeTest.userId = usersFromDb.get(0).getId();
        } else {
            userDbService.saveUser(new User(1L, "testEmail2323", "testHash12345", "testName123", Collections.emptyList()));
            var newUserFromDb = userDbService.getAllUsers().get(0);
            if (newUserFromDb == null) {
                throw new NullPointerException("Cannot create new user for tests.");
            } else {
                AssetFacadeTest.removeUserAfter = true;
                AssetFacadeTest.userId = newUserFromDb.getId();
            }
        }
    }

    @AfterAll
    static void afterAll(@Autowired TickerDbService tickerDbService, @Autowired UserDbService userDbService) {
        if (AssetFacadeTest.removeUserAfter) {
            userDbService.deleteUser(AssetFacadeTest.userId);
        }
        if (AssetFacadeTest.removeTickerAfter) {
            tickerDbService.deleteTicker(AssetFacadeTest.tickerId);
        }
    }

    @BeforeEach
    void beforeEach() throws GeneralSecurityException {
        assetFacade.setSignatureService(signatureService);
        AssetDto assetDto = new AssetDto(1L, AssetFacadeTest.tickerId, AssetFacadeTest.userId, "0.5", "400");
        var signature = new DigitalSignature(new byte[1], "");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        when(signatureService.validateSignature(any(String.class), any(String.class), any(DigitalSignature.class)))
                .thenReturn(true);
        assetInDb = assetFacade.createAsset(bodyRequest);
    }

    @AfterEach
    void afterEach() {
        try {
            var assetInDbId = assetInDb.getId();
            assetFacade.setSignatureService(signatureService);
            var signature = new DigitalSignature(new byte[1], "");
            assetFacade.deleteAsset(assetInDbId, signature);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createAsset() throws GeneralSecurityException {
        // given
        AssetDto assetDto = new AssetDto(1L, AssetFacadeTest.tickerId, AssetFacadeTest.userId, "0.5", "400");
        var signature = new DigitalSignature(new byte[1], "");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        //when
        var result = assetFacade.createAsset(bodyRequest);
        //then
        assertEquals(assetDto.getTickerId(), result.getTickerId());
        assertEquals(assetDto.getAmount(), result.getAmount());
        //cleanup
        deleteAsset(result.getId());
    }

    @Test
    void getAssets() throws GeneralSecurityException {
        //given
        var signature = new DigitalSignature(new byte[1], "");
        //when
        var assets = assetFacade.getAssets(AssetFacadeTest.userId, signature);
        //then
        assertTrue(assets.size() > 0);
    }

    @Test
    void updateAsset() throws GeneralSecurityException {
        // given
        var assetInDbId = assetInDb.getId();
        AssetDto assetDto = new AssetDto(assetInDbId, AssetFacadeTest.tickerId, AssetFacadeTest.userId, "0.77", "700");
        var signature = new DigitalSignature(new byte[1], "");
        AssetBodyRequest bodyRequest = new AssetBodyRequest(assetDto, signature);
        //when
        var result = assetFacade.updateAsset(bodyRequest);
        //then
        assertEquals(assetDto, result);
    }

    @Test
    void getAsset() {
        //given
        var assetInDbId = assetInDb.getId();
        AssetDto assetDto = new AssetDto(assetInDbId, AssetFacadeTest.tickerId, AssetFacadeTest.userId, "0.5", "400");
        //when
        var asset = assetFacade.getAsset(assetInDbId);
        //then
        assertEquals(assetDto, asset);
    }

    @Test
    void testDeleteAsset() throws GeneralSecurityException {
        // given
        var assetInDbId = assetInDb.getId();
        //when
        deleteAsset(assetInDbId);
        //then
        var assetShouldNotExist = assetFacade.getAsset(assetInDbId);
        assertNull(assetShouldNotExist);
    }

    @Test
    void ping() {
        // when
        var result = assetFacade.ping();
        //then
        assertEquals("pong", result);
    }

    void deleteAsset(Long assetId) throws GeneralSecurityException {
        var signature = new DigitalSignature(new byte[1], "");
        assetFacade.deleteAsset(assetId, signature);
    }
}