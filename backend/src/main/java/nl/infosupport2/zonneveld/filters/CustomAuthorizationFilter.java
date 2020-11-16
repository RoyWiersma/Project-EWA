package nl.infosupport2.zonneveld.filters;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import nl.infosupport2.zonneveld.TokenManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(CustomAuthenticationFilter.AUTH_HEADER_KEY);

        if (Objects.isNull(header) || !header.startsWith(TokenManager.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(CustomAuthenticationFilter.AUTH_HEADER_KEY);

        if (Objects.nonNull(header) && header.startsWith(TokenManager.TOKEN_PREFIX)) {
            try {
                String username = TokenManager.parseToken(header);

                return new UsernamePasswordAuthenticationToken(username, null, List.of());
            } catch (ExpiredJwtException e) {
                throw new AccessDeniedException("Token is verlopen");
            } catch (UnsupportedJwtException | MalformedJwtException e) {
                throw new AccessDeniedException("Token wordt niet ondersteund");
            } catch (Exception e) {
                throw new AccessDeniedException("Gebruiker kon niet worden geauthentiseerd");
            }
        } else {
            throw new AccessDeniedException("Authenticatie token niet gevonden");
        }
    }
}
