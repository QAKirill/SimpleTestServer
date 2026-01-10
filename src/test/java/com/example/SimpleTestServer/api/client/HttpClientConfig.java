package com.example.SimpleTestServer.api.client;

import com.example.SimpleTestServer.config.Stand;
import com.example.SimpleTestServer.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    //TODO:Создать и добавить фильтры
    @Bean
    public RequestSpecification requestSpecification(TestConfig testConfig) {
        Stand stand = testConfig.getStand(); //Просто для демо, допустим где-то в спеке используем

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                //.addFilter(new AllureRestAssured()) можно добавить несколько
                .build();
    }
}