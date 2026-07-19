package com.example.SimpleTestServer.api.client;

import com.example.SimpleTestServer.api.context.TestContext;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ApiClient extends BaseHttpClient {

    private final TestContext testContext;

    @Autowired
    public ApiClient(RequestSpecification spec, TestContext context, @LocalServerPort int port) {
        super(spec, port); // Отдаем базе только то, что ей нужно
        this.testContext = context; // А контекст оставляем себе
    }

    public Response getHello() {
        return sendGet(
                spec -> spec.basePath("/api/users/hello"),
                Response.class);
    }
}
