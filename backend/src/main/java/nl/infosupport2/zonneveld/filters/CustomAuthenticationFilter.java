package nl.infosupport2.zonneveld.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.infosupport2.zonneveld.CustomUserConverter;
import nl.infosupport2.zonneveld.TokenManager;
import nl.infosupport2.zonneveld.entities.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTH_HEADER_KEY = "Authorization";

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(CustomUserConverter.toAuthenticationToken(user));
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("De inloggegevens kloppen niet");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader(AUTH_HEADER_KEY, String.format(
                "%s%s",
                TokenManager.TOKEN_PREFIX,
                TokenManager.generateToken(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
            )
        );
    }
}
