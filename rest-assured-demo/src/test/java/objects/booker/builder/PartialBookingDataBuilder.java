package objects.booker.builder;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;
import objects.booker.BookingDates;

public class PartialBookingDataBuilder {

    private static final Faker faker = new Faker();

    public static PartialBooking createPartialBooking() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        return PartialBooking.builder()
                .totalprice(faker.number().numberBetween(150, 4000))
                .bookingdates(BookingDates.builder().checkin(formatter.format(faker.date().past(10, TimeUnit.DAYS)))
                        .checkout(formatter.format(faker.date().future(7, TimeUnit.DAYS)))
                        .build())
                .build();
    }
}
