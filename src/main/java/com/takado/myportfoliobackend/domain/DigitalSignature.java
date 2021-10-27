package com.takado.myportfoliobackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class DigitalSignature {
    private byte[] signature;
    private String message;
}
