package com.vvopaa.ega.core;

import com.vvopaa.ega.security.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
@Slf4j
public class AuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    if (jwtTokenProvider.validateToken(authToken)) {
      Claims claims = jwtTokenProvider.getClaimsFromToken(authToken);
      Set<RoleEnum> roles = new HashSet<>();
      try {
        claims.get(jwtTokenProvider.CLAIMS_ROLE_KEY, Set.class)
          .forEach(role -> roles.add(RoleEnum.valueOf(String.valueOf(role))));
      } catch (IllegalArgumentException e) {
        log.warn("Invalid Role in jwt has been given: " + e.getMessage());
      }
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        claims.getSubject(),
        null,
        roles.stream().map(roleEnum -> new SimpleGrantedAuthority(roleEnum.name())).collect(Collectors.toSet())
      );
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}
