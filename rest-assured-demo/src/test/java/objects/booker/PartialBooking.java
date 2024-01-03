package objects.booker;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PartialBooking {

    private Integer totalprice;
    private BookingDates bookingdates;
}
