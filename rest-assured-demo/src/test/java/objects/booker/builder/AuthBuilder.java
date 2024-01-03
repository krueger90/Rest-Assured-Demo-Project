package objects.booker.builder;

import objects.booker.AuthToken;

public class AuthBuilder {

    public static AuthToken createToken() {
        return AuthToken.builder()
                .username("admin")
                .password("password123")
                .build();
    }

}
