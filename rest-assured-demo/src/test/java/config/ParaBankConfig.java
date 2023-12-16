package config;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class ParaBankConfig {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://parabank.parasoft.com")
                .setBasePath("/parabank/services/bank")
                .setContentType("application/json")
                .addHeader("Accept", "application/json")
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
