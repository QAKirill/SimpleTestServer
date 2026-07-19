package com.example.SimpleTestServer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestConfig {
    private final Stand stand;
    private final String fromEnv;
    private final String dbUrl;
    private final String secretKey;

    public TestConfig(
            @Value("${stand:IFT}") Stand stand,
            @Value("${JAVA_HOME:}") String fromEnv,
            @Value("${app.database.url}") String dbUrl,
            @Value("${app.security.jwt-secret}") String secretKey
    ) {
        this.stand = stand;
        this.fromEnv = fromEnv;
        this.dbUrl = dbUrl;
        this.secretKey = secretKey;
    }
}
