package com.vvopaa.ega.security.controller;

import com.vvopaa.ega.common.payload.BasicResponse;
import com.vvopaa.ega.core.JwtTokenProvider;
import com.vvopaa.ega.security.model.SecurityUser;
import com.vvopaa.ega.security.payload.SignInRequest;
import com.vvopaa.ega.security.payload.SignInResponse;
import com.vvopaa.ega.security.payload.SignUpRequest;
import com.vvopaa.ega.security.service.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController("security")
@Slf4j
@AllArgsConstructor
public class SecurityController {
  private final SecurityService securityService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("sign-in")
  public Mono<ResponseEntity> signInUser(@Valid @RequestBody SignInRequest signInRequest) {
    return securityService.findByUsername(signInRequest.getUsernameOrEmail())
      .filter(UserDetails::isEnabled)
      .filter(userDetails -> passwordEncoder.matches(signInRequest.getPassword(), userDetails.getPassword()))
      .map(userDetails -> ResponseEntity.ok().body(new SignInResponse(jwtTokenProvider.generateToken(userDetails))))
      .cast(ResponseEntity.class)
      .defaultIfEmpty(ResponseEntity.badRequest().body("Username or password error"));
  }

  @PostMapping("sign-up")
  public Mono<ResponseEntity> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest, @ApiIgnore ServerHttpRequest serverHttpRequest) {
    return securityService
      .saveNewSecurityUser(signUpRequest.getLogin(), passwordEncoder.encode(signUpRequest.getPassword()))
      .map(userMono -> UriComponentsBuilder
        .fromHttpRequest(serverHttpRequest)
        .path("/users/{username}")
        .buildAndExpand(signUpRequest.getLogin()).toUri())
      .map(uri -> ResponseEntity.created(uri).build())
      .cast(ResponseEntity.class)
      .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.getMessage())));
  }
}
