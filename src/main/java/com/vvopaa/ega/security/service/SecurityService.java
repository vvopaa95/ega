package com.vvopaa.ega.security.service;

import com.vvopaa.ega.security.model.SecurityUser;
import com.vvopaa.ega.security.enums.RoleEnum;
import com.vvopaa.ega.security.exception.UsernameAlreadyExistsException;
import com.vvopaa.ega.security.repository.SecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@AllArgsConstructor
public class SecurityService implements ReactiveUserDetailsService {
  private final SecurityRepository securityRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return securityRepository.findByUsername(username).cast(UserDetails.class);
  }

  public Mono<SecurityUser> saveNewSecurityUser(String username, String password) {
    return securityRepository.findByUsername(username)
      .flatMap(securityUser ->
        securityUser == null
          ? securityRepository.save(new SecurityUser(username, password, Collections.singleton(RoleEnum.ROLE_USER)))
          : Mono.error(new UsernameAlreadyExistsException(securityUser.getUsername())));
  }
}
