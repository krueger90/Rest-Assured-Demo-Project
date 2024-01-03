import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import config.ParaBankConfig;
import config.ParaBankEndpoints;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

@TestMethodOrder(OrderAnnotation.class)
public class ParaBankTest extends ParaBankConfig implements ParaBankEndpoints {

    private static String savingsAccountId;
    private static String checkingAccountId;

    /*
     * The customerId was created manually because the API doesn't
     * offer an endpoint for user creation
     * An account is created automatically when creating the user (fromAccountId)
     */
    @Test
    @Order(1)
    public void createSavingsAccount() {
        savingsAccountId = 

        given()
                .queryParam("customerId", 12434)
                .queryParam("newAccountType", 1)
                .queryParam("fromAccountId", 13566)
        .when()
                .post(CREATE_ACCOUNT)
        .then()
                .body(matchesXsdInClasspath("ParaBankAccountSchema.xsd"))
                .statusCode(200)
                .assertThat()
                .body("account.id", notNullValue())
                .body("account.type", equalTo("SAVINGS"))
                .extract()
                .path("account.id");
    }

    @Test
    @Order(2)
    public void createCheckingAccount() {
        checkingAccountId = 
        
        given()
                .queryParam("customerId", 12434)
                .queryParam("newAccountType", 0)
                .queryParam("fromAccountId", 13566)
        .when()
                .post(CREATE_ACCOUNT)
        .then()
                .body(matchesXsdInClasspath("ParaBankAccountSchema.xsd"))
                .statusCode(200)
                .assertThat()
                .body("account.id", notNullValue())
                .body("account.type", equalTo("CHECKING"))
                .extract()
                .path("account.id");
    }

    @Test
    @Order(4)
    public void depositInCheckingAccount() {
        given()
                .queryParam("accountId", checkingAccountId)
                .queryParam("amount", 3500)
        .when()
                .post(DEPOSIT)
        .then()
                .statusCode(200);
    }

    /*
     * When a new account is created, it is automatically credited with $100,
     * hence the assertion amount
     */
    @Test
    @Order(5)
    public void getCheckingAccountBalance() {
        when()
                .get(SINGLE_ACCOUNT, checkingAccountId)
        .then()
                .assertThat()
                .statusCode(200)
                .body("account.balance", equalTo("3600.00"));
    }

    @Test
    @Order(6)
    public void depositInSavingsAccount() {
        given()
                .queryParam("accountId", savingsAccountId)
                .queryParam("amount", 400)
        .when()
                .post(DEPOSIT)
        .then()
                .statusCode(200);
    }

    /*
     * When a new account is created, it is automatically credited with $100,
     * hence the assertion amount
     */
    @Test
    @Order(7)
    public void getSavingsAccountBalance() {
        when()
                .get(SINGLE_ACCOUNT, savingsAccountId)
        .then()
                .assertThat()
                .statusCode(200)
                .body("account.balance", equalTo("500.00"));
    }

    @Test
    @Order(8)
    public void withdrawFromCheckingAccount() {
        given()
                .queryParam("accountId", checkingAccountId)
                .queryParam("amount", 500)
        .when()
                .post(WITHDRAW)
        .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("Successfully withdrew $500 from account #" + checkingAccountId));
    }

    @Test
    @Order(9)
    public void withdrawFromSavingsAccount() {
        given()
                .queryParam("accountId", savingsAccountId)
                .queryParam("amount", 200)
        .when()
                .post(WITHDRAW)
        .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("Successfully withdrew $200 from account #" + savingsAccountId));
    }

    @Test
    @Order(10)
    public void transferFunds() {
        given()
            .queryParam("fromAccountId", checkingAccountId)
            .queryParam("toAccountId", savingsAccountId)
            .queryParam("amount", 1000)
        .when()
            .post(TRANSFER)
        .then()
            .statusCode(200)
            .assertThat()
            .body(containsString("Successfully transferred $1000 from account #" + checkingAccountId + " to account #" + savingsAccountId));
    }

    @Test
    @Order(11)
    public void payBill() {
      String payee ="{\r\n  \"name\": \"John Doe\",\r\n  \"address\": {\r\n    \"street\": \"1st Bvd\",\r\n    \"city\": \"London\",\r\n    \"state\": \"UK\",\r\n    \"zipCode\": \"1236\"\r\n  },\r\n  \"phoneNumber\": \"4560012336\",\r\n  \"accountNumber\": 23600\r\n}";

        given()
            .header("Content-Type", "application/json")
            .queryParam("accountId", 13566)
            .queryParam("amount", 300)
            .body(payee)
        .when()
            .post(PAY_BILL)
        .then()
            .statusCode(200)
            .body(matchesXsdInClasspath("ParaBankBillPaySchema.xsd"))
            .assertThat()
            .body("billPayResult.accountId", equalTo("13566"), "billPayResult.amount", equalTo("300"), "billPayResult.payeeName", equalTo("John Doe"));    
    }
}
