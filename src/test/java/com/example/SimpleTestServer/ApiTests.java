package com.example.SimpleTestServer;

import com.example.SimpleTestServer.config.TestConfig;
import com.example.SimpleTestServer.testcore.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApiTests extends TestBase {

	@Autowired
	private TestConfig config;

	@Test
	void contextLoads() {
		Response response = RestAssured
				.given()
				.basePath("/api/users")
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

	@Test
	void configTest() {
		TestConfig config = getConfig();

		assertAll("Grouped Config assertions",
				() -> assertThat(config.getStand()).isNotNull(),
				() -> assertThat(config.getFromEnv()).isNotNull(),
				() -> assertThat(config.getDbUrl()).isNotNull()
		);
	}
}
