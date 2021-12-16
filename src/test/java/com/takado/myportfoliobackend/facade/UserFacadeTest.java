package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.domain.requests.UserBodyRequest;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.GeneralSecurityException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserFacadeTest {
    @Autowired
    UserFacade userFacade;

    @Mock
    RequestSignatureService signatureService;

    UserDto userInDb;

    @BeforeEach
    void beforeEach() throws GeneralSecurityException {
        // given
        userFacade.setSignatureService(signatureService);
        UserDto userDto = new UserDto(1L, "mail", "123", "aa", Collections.emptyList());
        var signature = new DigitalSignature(new byte[1],
                "UserDto(id=1, email=mail, nameHash=123, displayedName=aa, assetsId=[])");
        UserBodyRequest bodyRequest = new UserBodyRequest(signature, userDto);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        userInDb = userFacade.createUser(bodyRequest);
    }

    @AfterEach
    void afterEach() throws GeneralSecurityException {
        try {
            // given
            var userInDbId = userInDb.getId();
            userFacade.setSignatureService(signatureService);
            var signature = new DigitalSignature(new byte[1],
                    "http://localhost:8081/v1/users/delete/" + userInDbId);
            when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
            //when
            userFacade.deleteUser(userInDbId, signature);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getUser() {
        var result = userFacade.getUser(userInDb.getId());
        assertNotNull(result);
        assertEquals(userInDb, result);
    }

    @Test
    void createUser() throws GeneralSecurityException {
        // given
        userFacade.setSignatureService(signatureService);
        UserDto userDto = new UserDto(1L, "mail22", "123", "aa", Collections.emptyList());
        var signature = new DigitalSignature(new byte[1],
                "UserDto(id=1, email=mail22, nameHash=123, displayedName=aa, assetsId=[])");
        UserBodyRequest bodyRequest = new UserBodyRequest(signature, userDto);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var result = userFacade.createUser(bodyRequest);
        //then
        assertEquals(userDto, result);
    }

    @Test
    void deleteUser() throws GeneralSecurityException {
        // given
        var userInDbId = userInDb.getId();
        userFacade.setSignatureService(signatureService);
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/users/delete/" + userInDbId);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        userFacade.deleteUser(userInDbId, signature);
        //then
        var assetShouldNotExist = userFacade.getUser(userInDbId);
        assertNull(assetShouldNotExist);
    }

    @Test
    void ping() {
        // when
        var result = userFacade.ping();
        //then
        assertEquals("pong", result);
    }

    @Test
    void testGetUser() throws GeneralSecurityException {
        userFacade.setSignatureService(signatureService);
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/users/mail");
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);

        var result = userFacade.getUser(userInDb.getEmail(), signature);
        assertNotNull(result);
        assertEquals(userInDb, result);
    }
}