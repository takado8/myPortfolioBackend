package com.takado.myportfoliobackend.service;

import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Service
public class RequestSignatureService {
    // todo: move key to application.properties
    private final String publicKeyString = "MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAO9nKmRN7dwPRzsv0/UvWjiKKuoc0xvYyFaYoH5Xct0Os2Cz2yJNu6Cdgl0VUGDFX2M7vr3cpyEnDpmU2ssN2cMYkOxcOq/aFNH5M6nmBFA05VLW85XHRTcLxUSbBFvkLjYM6wJW0Jd98IM8WuKNxk3VfeH8XONJXFcNl2DgXQdz";
    Base64.Decoder base64Decoder = Base64.getDecoder();

    public boolean verifyDigitalSignature(byte[] digitalSignature, String message) throws GeneralSecurityException {
        PublicKey publicKey = loadPublicKey(publicKeyString);
        Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
        signature.initVerify(publicKey);
        byte[] bytes = message.getBytes();
        signature.update(bytes);
        return signature.verify(digitalSignature);
    }

    public PublicKey loadPublicKey(String publicKeyString) throws GeneralSecurityException {
        byte[] data = base64Decoder.decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(spec);
    }
}
