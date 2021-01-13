package nl.infosupport2.zonneveld;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserConverter {

    public static User toSpringUser(nl.infosupport2.zonneveld.entities.User user) {
        return new User(user.getEmail(), user.getPassword(), List.of());
    }

    public static UsernamePasswordAuthenticationToken toAuthenticationToken(nl.infosupport2.zonneveld.entities.User user) {
        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), List.of());
    }
}
