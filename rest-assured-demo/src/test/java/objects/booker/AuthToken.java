package objects.booker;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthToken {

    private String username;
    private String password;
}
