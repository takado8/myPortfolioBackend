package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.DigitalSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Service
public class RequestSignatureService {
    @Value("${signature.publicKeyString}")
    private String publicKeyString;
    private PublicKey publicKey;

    public boolean validateSignature(String expected, String signed, DigitalSignature signature) throws GeneralSecurityException {
        if ((expected).equals(signed)) {
            if (verifyDigitalSignature(signature)) {
                return true;
            } else {
                System.out.println("Signature error.");
            }
        } else {
            System.out.println("Object different from signed.\nObject: " + expected
                    + "\nsigned: " + signed);
        }
        return false;
    }

    public boolean verifyDigitalSignature(DigitalSignature digitalSignature) throws GeneralSecurityException {
        if (digitalSignature == null) return false;
        byte[] signatureBytes = digitalSignature.getSignature();
        String message = digitalSignature.getMessage();
        if (signatureBytes == null || signatureBytes.length < 1 || message == null || message.isBlank()) return false;
        if (this.publicKey == null) this.publicKey = loadPublicKey(publicKeyString);
        Signature signatureService = Signature.getInstance("SHA1withDSA", "SUN");
        signatureService.initVerify(publicKey);
        byte[] bytes = message.getBytes();
        signatureService.update(bytes);
        return signatureService.verify(signatureBytes);
    }

    public PublicKey loadPublicKey(String publicKeyString) throws GeneralSecurityException {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] data = base64Decoder.decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(spec);
    }
}
