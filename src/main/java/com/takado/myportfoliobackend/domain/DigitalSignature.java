package com.takado.myportfoliobackend.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DigitalSignature {
    private byte[] signature;
    private String message;
}
