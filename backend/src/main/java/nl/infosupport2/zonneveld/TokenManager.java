package nl.infosupport2.zonneveld;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

public class TokenManager {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final int TOKEN_EXPIRY_DURATION = 30; // in days

    public static final String TOKEN_PREFIX = "Bearer ";

    public static String generateToken(String subject) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(TOKEN_EXPIRY_DURATION).toInstant()))
            .signWith(SECRET_KEY)
            .compact();
    }

    public static String parseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody()
            .getSubject();
    }
}
