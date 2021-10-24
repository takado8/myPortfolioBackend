package com.takado.myportfoliobackend.domain;

import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    private final static String adminToken = "Cvf4sefAE53WEdQERd4lb3451H";

    public boolean verifyAccessToken(String token) {
        return token.equals(adminToken);
    }
}
