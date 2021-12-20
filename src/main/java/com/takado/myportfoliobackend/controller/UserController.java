package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.domain.requests.UserBodyRequest;
import com.takado.myportfoliobackend.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @PostMapping("/ping")
    public String ping() {
        return userFacade.ping();
    }

    @PostMapping({"{email}"})
    public UserDto getUser(@PathVariable String email, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        return userFacade.getUserByEmail(email, digitalSignature);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserBodyRequest bodyRequest) throws GeneralSecurityException {
        return userFacade.createUser(bodyRequest);
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id, @RequestBody DigitalSignature digitalSignature) throws GeneralSecurityException {
        userFacade.deleteUser(id, digitalSignature);
    }
}
