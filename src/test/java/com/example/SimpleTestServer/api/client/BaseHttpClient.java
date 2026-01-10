package com.example.SimpleTestServer.api.client;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {
    protected final RequestSpecification spec;
    private final int port;

    protected BaseHttpClient(RequestSpecification spec, int port) {
        this.spec = spec;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    protected <T> T sendPost(Consumer<RequestSpecification> modification, Class<T> responseClass) {
        RequestSpecification currentSpec = getCurrentSpec(modification);

        ValidatableResponse response = currentSpec.when().post().then().log().body();

        if (responseClass.equals(Response.class)) {
            return (T) response.extract().response();
        }

        return response.extract().as(responseClass);
    }

    @SuppressWarnings("unchecked")
    protected <T> T sendGet(Consumer<RequestSpecification> modification, Class<T> responseClass) {
        RequestSpecification currentSpec = getCurrentSpec(modification);

        ValidatableResponse response = currentSpec.when().get().then().log().body();

        if (responseClass.equals(Response.class)) {
            return (T) response.extract().response();
        }

        return response.extract().as(responseClass);
    }

    private RequestSpecification getCurrentSpec(Consumer<RequestSpecification> modification) {
        RequestSpecification currentSpec = given().spec(spec).port(port);

        if (modification != null) {
            modification.accept(currentSpec);
        }

        return currentSpec;
    }
}
