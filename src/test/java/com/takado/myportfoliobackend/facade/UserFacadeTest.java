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
        userFacade.setSignatureService(signatureService);
        UserDto userDto = new UserDto(1L, "mail", "123", "aa", Collections.emptyList());
        var signature = new DigitalSignature(new byte[1],
                "UserDto(id=1, email=mail, nameHash=123, displayedName=aa, assetsId=[])");
        UserBodyRequest bodyRequest = new UserBodyRequest(signature, userDto);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        userInDb = userFacade.createUser(bodyRequest);
    }

    @AfterEach
    void afterEach() {
        try {
            var userInDbId = userInDb.getId();
            deleteUserById(userInDbId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteUserById(Long userId) throws GeneralSecurityException {
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/users/delete/" + userId);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);

        userFacade.deleteUser(userId, signature);
    }

    @Test
    void getUserById() {
        //when
        var result = userFacade.getUserById(userInDb.getId());
        //then
        assertNotNull(result);
        assertEquals(userInDb, result);
    }

    @Test
    void createUser() throws GeneralSecurityException {
        //given
        UserDto userDto = new UserDto(5L, "mail2233", "123456", "aabb", Collections.emptyList());
        var signature = new DigitalSignature(new byte[1],
                "UserDto(id=5, email=mail2233, nameHash=123456, displayedName=aabb, assetsId=[])");
        UserBodyRequest bodyRequest = new UserBodyRequest(signature, userDto);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var result = userFacade.createUser(bodyRequest);
        //then
        assertEquals(userDto.getEmail(), result.getEmail());
        //cleanup
        deleteUserById(result.getId());
    }

    @Test
    void deleteUser() throws GeneralSecurityException {
        //given
        var userInDbId = userInDb.getId();
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/users/delete/" + userInDbId);
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        userFacade.deleteUser(userInDbId, signature);
        //then
        var userShouldNotExist = userFacade.getUserById(userInDbId);
        assertNull(userShouldNotExist);
    }

    @Test
    void ping() {
        // when
        var result = userFacade.ping();
        //then
        assertEquals("pong", result);
    }

    @Test
    void testGetUserByEmail() throws GeneralSecurityException {
        //given
        var signature = new DigitalSignature(new byte[1],
                "http://localhost:8081/v1/users/mail");
        when(signatureService.verifyDigitalSignature(signature)).thenReturn(true);
        //when
        var result = userFacade.getUserByEmail(userInDb.getEmail(), signature);
        //then
        assertNotNull(result);
        assertEquals(userInDb, result);
    }
}