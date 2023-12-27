package objects.booker;

public class AuthBuilder {

    public static AuthToken createToken() {
        return AuthToken.builder()
                .username("admin")
                .password("password123")
                .build();
    }

}
