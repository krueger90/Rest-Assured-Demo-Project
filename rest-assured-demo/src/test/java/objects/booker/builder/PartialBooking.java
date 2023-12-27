package objects.booker.builder;

import lombok.Builder;
import lombok.Getter;
import objects.booker.BookingDates;

@Getter
@Builder
public class PartialBooking {

    private Integer totalprice;
    private BookingDates bookingdates;
}
