package com.example.SimpleTestServer.testcore;

import com.example.SimpleTestServer.SimpleTestServerApplication;
import com.example.SimpleTestServer.config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = SimpleTestServerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class TestBase {
    @LocalServerPort
    private int port;  // Инжект случайного порта сервера

    private static final ThreadLocal<TestConfig> CONFIG_HOLDER = new ThreadLocal<>();

    @BeforeAll
    static void initConfig(@Autowired TestConfig globalConfig) {
        TestConfig threadConfig = createThreadConfig(globalConfig);
        CONFIG_HOLDER.set(threadConfig);
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @AfterAll
    static void cleanup() {
        CONFIG_HOLDER.remove();
    }

    protected static TestConfig getConfig() {
        return CONFIG_HOLDER.get();
    }

    private static TestConfig createThreadConfig(TestConfig base) {
        return base;
    }
}
