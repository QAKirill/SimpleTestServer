package com.example.SimpleTestServer;

import com.example.SimpleTestServer.testcore.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ApiTests extends TestBase {

	@Test
	void contextLoads() {
		Response response = RestAssured
				.given()
				.basePath("/api")
				.when()
				.get("/hello")
				.then()
				.log()
				.body()
				.statusCode(200)
				.extract().response();

		String message = response.jsonPath().getString("message");

		assertThat(message)
				.withFailMessage("Что-то пошло не так!")
				.isEqualTo("Привет! Сервер работает!");
	}
}
