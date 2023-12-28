import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import config.BookerConfig;
import config.BookerEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;
import static objects.booker.AuthBuilder.createToken;
import objects.booker.AuthToken;
import objects.booker.Booking;
import static objects.booker.builder.BookingDataBuilder.createBooking;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(OrderAnnotation.class)
public class BookerTest extends BookerConfig implements BookerEndpoints {

    private static int bookingId;
    private static String authToken;
    private static Booking bookingData;

    @BeforeAll
    public static void testSetup() {
        bookingData = createBooking();
    }

    private void getToken() {
        AuthToken token = createToken();

        authToken = given()
                .body(token)
                .when()
                .post(GET_TOKEN)
                .then().assertThat()
                .body("token", is(notNullValue()))
                .extract()
                .path("token").toString();
    }

    @Test
    @Order(1)
    public void newBooking() {
        bookingId = given()
                .body(bookingData)
                .when()
                .post(ALL_BOOKINGS)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateBookingJsonSchema.json"))
                .and()
                .statusCode(200)
                .assertThat()
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(bookingData.getFirstname()), "booking.lastname",
                        equalTo(bookingData.getLastname()), "booking.totalprice", equalTo(bookingData.getTotalprice()),
                        "booking.depositpaid", equalTo(bookingData.getDepositpaid()),
                        "booking.bookingdates.checkin", equalTo(bookingData.getBookingdates().getCheckin()),
                        "booking.bookingdates.checkout", equalTo(bookingData.getBookingdates().getCheckout()),
                        "booking.additionalneeds", equalTo(bookingData.getAdditionalneeds()))
                .extract()
                .path("bookingid");
    }

    @Test
    @Order(2)
    public void getBooking() {
        when()
                .get(SINGLE_BOOKING, BookerTest.bookingId)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("BookerResponseJsonSchema.json"))
                .and()
                .statusCode(200)
                .assertThat()
                .body("firstname", equalTo(bookingData.getFirstname()), "lastname",
                        equalTo(bookingData.getLastname()), "totalprice", equalTo(bookingData.getTotalprice()),
                        "depositpaid", equalTo(bookingData.getDepositpaid()),
                        "bookingdates.checkin", equalTo(bookingData.getBookingdates().getCheckin()),
                        "bookingdates.checkout", equalTo(bookingData.getBookingdates().getCheckout()),
                        "additionalneeds", equalTo(bookingData.getAdditionalneeds()));
    }

    @Test
    @Order(3)
    public void updateBooking() {
        getToken();
        given()
                .header("Cookie", "token=" + BookerTest.authToken)
                .body(bookingData)
                .when()
                .put(SINGLE_BOOKING, BookerTest.bookingId)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("BookerResponseJsonSchema.json"))
                .assertThat()
                .body("firstname", equalTo(bookingData.getFirstname()), "lastname",
                        equalTo(bookingData.getLastname()), "totalprice",
                        equalTo(bookingData.getTotalprice()),
                        "depositpaid", equalTo(bookingData.getDepositpaid()),
                        "bookingdates.checkin", equalTo(bookingData.getBookingdates().getCheckin()),
                        "bookingdates.checkout",
                        equalTo(bookingData.getBookingdates().getCheckout()),
                        "additionalneeds", equalTo(bookingData.getAdditionalneeds()));
    }

    @Test
    @Order(4)
    public void partialUpdateBooking() {
        getToken();
        given()
                .header("Cookie", "token=" + BookerTest.authToken)
                .body(bookingData)
                .when()
                .patch(SINGLE_BOOKING, BookerTest.bookingId)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("BookerResponseJsonSchema.json"))
                .assertThat()
                .body("totalprice", equalTo(bookingData.getTotalprice()),
                        "bookingdates.checkin", equalTo(bookingData.getBookingdates().getCheckin()),
                        "bookingdates.checkout",
                        equalTo(bookingData.getBookingdates().getCheckout()));
    }

    @Test
    @Order(5)
    public void deleteBooking() {
        getToken();
        given().header("Cookie", "token=" + BookerTest.authToken)
                .when()
                .delete(SINGLE_BOOKING, BookerTest.bookingId)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(6)
    public void bookingIsDeleted() {
        given().get(SINGLE_BOOKING, BookerTest.bookingId)
                .then()
                .statusCode(404);
    }
}