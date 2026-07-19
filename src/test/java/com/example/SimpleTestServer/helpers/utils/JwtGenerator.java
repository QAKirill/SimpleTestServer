package com.example.SimpleTestServer.helpers.utils;

import com.example.SimpleTestServer.config.TestConfig;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {
    private final TestConfig testConfig;

    @Autowired
    public JwtGenerator(TestConfig testConfig) {
        this.testConfig = testConfig;
    }

    public String createToken(List<String> scopes) throws Exception {
        JWSSigner signer = new MACSigner(testConfig.getSecretKey());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("test-user")
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 3600 * 1000))
                .claim("scope", String.join(" ", scopes)) // Spring Security ищет скоупы тут
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}