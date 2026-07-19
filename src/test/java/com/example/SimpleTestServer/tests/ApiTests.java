package com.example.SimpleTestServer.tests;

import com.example.SimpleTestServer.api.client.ApiClient;
import com.example.SimpleTestServer.config.TestConfig;
import com.example.SimpleTestServer.testcore.TestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApiTests extends TestBase {

	@Autowired
	private TestConfig config;

	@Autowired
	private ApiClient apiClient;

	@Test
	void contextLoads() {
		Response response = apiClient.getHello();

		assertThat(response.statusCode()).isEqualTo(200);

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
