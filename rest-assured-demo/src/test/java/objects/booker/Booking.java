package objects.booker;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Booking {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}
