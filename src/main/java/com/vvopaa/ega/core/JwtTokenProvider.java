package com.vvopaa.ega.core;

import com.vvopaa.ega.core.objects.CustomOptions;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenProvider {
  final String CLAIMS_ROLE_KEY = "role";
  private final CustomOptions options;

  private Map<String, Object> generateClaims(UserDetails user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIMS_ROLE_KEY, Set.of(user.getAuthorities()));
    return claims;
  }

  Claims getClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(options.getJwtSecret()).parseClaimsJws(token).getBody();
  }

  public String generateToken(UserDetails userPrincipal) {
    final Date createdDate = new Date();
    final Date expiryDate = new Date(createdDate.getTime() + options.getJwtExpirationMs());
    return Jwts.builder()
      .setClaims(generateClaims(userPrincipal))
      .setSubject(userPrincipal.getUsername())
      .setIssuedAt(createdDate)
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, options.getJwtSecret())
      .compact();
  }

  boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(options.getJwtSecret()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      log.warn("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.warn("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.warn("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.warn("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.warn("JWT claims string is empty.");
    }
    return false;
  }
}
