package com.takado.myportfoliobackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    @Value("${admin.AccessToken}")
    private String adminToken;

    public boolean verifyAccessToken(String token) {
        return token.equals(adminToken);
    }
}
