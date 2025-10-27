package com.example.SimpleTestServer.testcore;

import com.example.SimpleTestServer.SimpleTestServerApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
        classes = SimpleTestServerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestBase {
    @LocalServerPort
    private int port;  // Инжект случайного порта сервера

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }
}
