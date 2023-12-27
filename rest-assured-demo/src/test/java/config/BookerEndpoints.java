package config;

public interface BookerEndpoints {

    /*
     * Used for all CRUD operations in the booker API
     */
    String GET_TOKEN = "/auth";
    String ALL_BOOKINGS = "/booking";
    String SINGLE_BOOKING = "booking/{id}";
}