package com.example.SimpleTestServer.api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ApiClient extends BaseHttpClient {

    public ApiClient(RequestSpecification spec, @LocalServerPort int port) {
        super(spec, port);
    }

    public Response getHello() {
        return sendGet(
                spec -> spec.basePath("/api/users/hello"),
                Response.class);
    }
}
