package com.takado.myportfoliobackend.facade;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.domain.requests.UserBodyRequest;
import com.takado.myportfoliobackend.mapper.UserMapper;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import com.takado.myportfoliobackend.service.UserDbService;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;


@Component
public class UserFacade {
    private final UserDbService dbService;
    private final UserMapper mapper;
    private RequestSignatureService signatureService;
    private final String apiPath = "http://localhost:8081/v1/users";

    public UserFacade(UserDbService dbService, UserMapper mapper, RequestSignatureService signatureService) {
        this.dbService = dbService;
        this.mapper = mapper;
        this.signatureService = signatureService;
    }

    public UserDto getUserByEmail(String email, DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/" + email;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                User user = dbService.getUserByEmail(email);
                if (user != null) return mapper.mapToDto(user);
            } else {
                System.out.println("Signature error in getUser.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
        return new UserDto(null, null, null, null, null);
    }

    public UserDto createUser(UserBodyRequest bodyRequest) throws GeneralSecurityException {
        DigitalSignature digitalSignature = bodyRequest.getDigitalSignature();
        UserDto userDto = bodyRequest.getUserDto();
        String userDtoString = userDto.toString();
        String signatureAssetDtoString = digitalSignature.getMessage();
        if (userDtoString.equals(signatureAssetDtoString)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                User userFromDb = dbService.getUserByEmail(userDto.getEmail());
                if (userFromDb == null) {
                    return mapper.mapToDto(dbService.saveUser(mapper.mapToUser(userDto)));
                } else {
                    return mapper.mapToDto(userFromDb);
                }
            } else {
                System.out.println("Signature verification failed.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + userDtoString
                    + "\nsigned: " + signatureAssetDtoString);
        }
        return new UserDto(null, null, null, null, null);
    }

    public void deleteUser(Long id, DigitalSignature digitalSignature) throws GeneralSecurityException {
        String receivedDataPath = apiPath + "/delete/" + id;
        String signedPath = digitalSignature.getMessage();
        if ((receivedDataPath).equals(signedPath)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                dbService.deleteUser(id);
            } else {
                System.out.println("Signature error in getUser.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + receivedDataPath
                    + "\nsigned: " + signedPath);
        }
    }

    public String ping() {
        return "pong";
    }

    public UserDto getUserById(Long id) {
        var user = dbService.getUserById(id);
        return user == null ? null : mapper.mapToDto(user);
    }

    public void setSignatureService(RequestSignatureService signatureService) {
        this.signatureService = signatureService;
    }
}
