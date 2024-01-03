package config;

import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ParaBankConfig {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://parabank.parasoft.com")
                .setBasePath("/parabank/services/bank")
                .setContentType("application/xml")
                .addHeader("Accept", "*/*")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(20000L))
                .build();
    }
}
