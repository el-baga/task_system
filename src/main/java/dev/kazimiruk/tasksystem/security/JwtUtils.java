package dev.kazimiruk.tasksystem.security;

import dev.kazimiruk.tasksystem.entity.Person;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer lifeTime;

    private static final SignatureAlgorithm tokenAlg = SignatureAlgorithm.HS256;

    public String generateToken(@NonNull final Person person) {
        final Map<String, Object> claims = collectClaims(person);
        final Date issuedDate = new Date();
        final Date expiredDate = new Date(issuedDate.getTime() + lifeTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(person.getId()))
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(tokenAlg, secret)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).requireExpiration(getExpiration(token)).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | IllegalArgumentException |
                 MalformedJwtException | UnsupportedJwtException |
                 SignatureException | IncorrectClaimException |
                 MissingClaimException ex) {
            return false;
        }
    }

    @NonNull
    private static Map<String, Object> collectClaims(@NonNull final Person person) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of("ROLE_USER"));
        claims.put("email", person.getEmail());
        claims.put("userId", person.getId());
        return claims;
    }

    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    private String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private List<String> getRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    private Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(
                getSubject(token),
                null,
                getRoles(token)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
