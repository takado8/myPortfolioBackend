package com.takado.myportfoliobackend.domain.requests;


import com.takado.myportfoliobackend.domain.DigitalSignature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BodyRequest {
    private DigitalSignature digitalSignature;
}
