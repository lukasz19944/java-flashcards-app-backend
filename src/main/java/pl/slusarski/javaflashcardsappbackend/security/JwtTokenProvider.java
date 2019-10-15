package pl.slusarski.javaflashcardsappbackend.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.slusarski.javaflashcardsappbackend.domain.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.slusarski.javaflashcardsappbackend.security.SecurityConstants.EXPIRATION_TIME;
import static pl.slusarski.javaflashcardsappbackend.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("authority", roles);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);

            return true;
        } catch (SignatureException exc) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException exc) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException exc) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException exc) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException exc) {
            System.out.println("JWT claims string is empty");
        }

        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

    public List<SimpleGrantedAuthority> getUserRolesFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("authority")).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());

        return authorities;
    }
}