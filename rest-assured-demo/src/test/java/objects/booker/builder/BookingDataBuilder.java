package objects.booker.builder;

import com.github.javafaker.Faker;

import objects.booker.BookingDates;
import objects.booker.Booking;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingDataBuilder {

    private static final Faker faker = new Faker();

    public static Booking createBooking() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        return Booking.builder().firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(150, 4000))
                .depositpaid(true)
                .bookingdates(BookingDates.builder().checkin(formatter.format(faker.date().past(10, TimeUnit.DAYS)))
                        .checkout(formatter.format(faker.date().future(7, TimeUnit.DAYS)))
                        .build())
                .additionalneeds("Extra blanket")
                .build();
    }
}
