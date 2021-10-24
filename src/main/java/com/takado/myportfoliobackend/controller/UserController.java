package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.AssetDto;
import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.domain.requests.UserBodyRequest;
import com.takado.myportfoliobackend.mapper.UserMapper;
import com.takado.myportfoliobackend.service.RequestSignatureService;
import com.takado.myportfoliobackend.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserDbService dbService;
    private final UserMapper mapper;
    private final RequestSignatureService signatureService;
    private final String apiPath = "http://localhost:8081/v1/users";

    // todo: remove this endpoint
    @GetMapping
    public List<UserDto> getAllUsers() {
        return mapper.mapToDtoList(dbService.getAllUsers());
    }

    @PostMapping({"{email}"})
    public UserDto getUser(@PathVariable String email, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
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
        return new UserDto(null, null,null, null, null);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserBodyRequest bodyRequest) throws GeneralSecurityException {
        DigitalSignature digitalSignature = bodyRequest.getDigitalSignature();
        UserDto userDto = bodyRequest.getUserDto();
        String userDtoString = userDto.toString();
        String signatureAssetDtoString = digitalSignature.getMessage();
        if (userDtoString.equals(signatureAssetDtoString)) {
            if (signatureService.verifyDigitalSignature(digitalSignature)) {
                return mapper.mapToDto(dbService.saveUser(mapper.mapToUser(userDto)));

            } else {
                System.out.println("Signature verification failed.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + userDtoString
                    + "\nsigned: " + signatureAssetDtoString);
        }
        return new UserDto(null,null,null,null,null);
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
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
}
