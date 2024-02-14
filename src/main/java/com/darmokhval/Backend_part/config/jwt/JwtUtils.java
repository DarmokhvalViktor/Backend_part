package com.darmokhval.Backend_part.config.jwt;


import com.darmokhval.Backend_part.models.entity.ERole;
import com.darmokhval.Backend_part.security.MyCustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${darmokhval.app.jwtSecret}")
    private String jwtSecret;
    @Value("${darmokhval.app.jwtAccessTokenExpirationMs}")
    private int jwtAccessTokenExpirationMs;
    @Value("${darmokhval.app.jwtRefreshTokenExpirationMs}")
    private int jwtRefreshTokenExpirationMs;

    public String generateAccessToken(Authentication authentication) {
        MyCustomUserDetails userPrincipal = (MyCustomUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claims(Map.of("roles", roles))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtAccessTokenExpirationMs))
                .signWith(key())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        MyCustomUserDetails userPrincipal = (MyCustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claims(Map.of("type", "refresh"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshTokenExpirationMs))
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is not supported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public boolean isRefreshToken(String token) {
        return resolveClaim(token, claims -> Objects.equals(claims.get("type", String.class), "refresh"));
    }
    public Date extractExpiration(String token) {
        return resolveClaim(token, Claims::getExpiration);
    }
    private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts
                .parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
/** Do code below work in the same way as validateJwtToken() method? TODO check this methods
 *     public boolean isTokenExpired(String token) {
 *         return resolveClaim(token, Claims::getExpiration).before(new Date());
 *     }
 *
 *     public boolean isRefreshToken(String token) {
 *         return resolveClaim(token, claims -> Objects.equals(claims.get("type", String.class), "refresh"));
 *     }
 *
 *     public String extractUsername(String token) {
 *         return resolveClaim(token, Claims::getSubject);
 *     }
 *
 *     public Date extractExpiration(String token) {
 *         return resolveClaim(token, Claims::getExpiration);
 *     }
 *
 *     private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
 *         Claims claims = Jwts
 *                 .parserBuilder()
 *                 .setSigningKey(key)
 *                 .build()
 *                 .parseClaimsJws(token)
 *                 .getBody();
 *         return resolver.apply(claims);
 *     }
 */

}
