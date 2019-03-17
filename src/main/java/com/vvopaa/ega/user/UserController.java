package com.vvopaa.ega.user;

import com.vvopaa.ega.core.JwtTokenProvider;
import com.vvopaa.ega.email.EmailSenderService;
import com.vvopaa.ega.user.confirmationtoken.ConfirmationToken;
import com.vvopaa.ega.user.confirmationtoken.ConfirmationTokenService;
import com.vvopaa.ega.user.enums.RoleEnum;
import com.vvopaa.ega.user.payload.SignInRequest;
import com.vvopaa.ega.user.payload.SignInResponse;
import com.vvopaa.ega.user.payload.SignUpRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mail.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collections;

@RestController("security")
@Slf4j
@AllArgsConstructor
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final ConfirmationTokenService confirmationTokenService;
  private final EmailSenderService emailSenderService;

  @PostMapping("sign-in")
  public Mono<ResponseEntity> signInUser(@Valid @RequestBody SignInRequest signInRequest) {
    return userService
      .findByUsername(signInRequest.getUsernameOrEmail())
      .filter(UserDetails::isEnabled)
      .filter(userDetails -> passwordEncoder.matches(signInRequest.getPassword(), userDetails.getPassword()))
      .map(userDetails -> ResponseEntity.ok().body(new SignInResponse(jwtTokenProvider.generateToken(userDetails))))
      .cast(ResponseEntity.class)
      .defaultIfEmpty(ResponseEntity.badRequest().body("Username or password error"));
  }

  @PostMapping("sign-up")
  public Mono<ResponseEntity> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest, @ApiIgnore ServerHttpRequest serverHttpRequest) {
    return userService
      .saveUnique(
        new User(
          signUpRequest.getLogin(),
          passwordEncoder.encode(signUpRequest.getPassword()),
          Collections.singleton(RoleEnum.ROLE_USER),
          signUpRequest.getUserInfo()))
      .flatMap(user -> confirmationTokenService.save(new ConfirmationToken(user.getId())))
      .flatMap(confirmationToken -> {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(signUpRequest.getLogin());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("ega-no-reply@gmail.com");
        mailMessage.setText(String.format(EmailSenderService.TPL_MSG_FORMAT, confirmationToken.getToken()));
        return emailSenderService.sendEmail(mailMessage); // just for testing: Mono.just(Void.class)
      })
      .map(aVoid -> UriComponentsBuilder
        .fromHttpRequest(serverHttpRequest)
        .path("/users/{username}")
        .buildAndExpand(signUpRequest.getLogin())
        .toUri())
      .map(uri -> ResponseEntity.created(uri).build())
      .cast(ResponseEntity.class)
      .onErrorResume(throwable -> {
        //Class<?> throwableClass = throwable.getClass();
        //ResponseEntity response = ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.getMessage());
        return Mono.just(throwable)
          .filter(throwableCurr -> throwableCurr.getClass().getSuperclass().equals(MailException.class))
          .flatMap(throwableReal -> userService
            .findUserByUsername(signUpRequest.getLogin())
            .flatMap(user -> confirmationTokenService.deleteByUserId(user.getId()).thenReturn(user))
            .flatMap(user -> userService.deleteById(user.getId())))
          .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.getMessage()));
      });
  }

  @GetMapping("/confirm-account")
  public Mono<ResponseEntity> confirmUserAccount(@RequestParam("token") String token) {
    return confirmationTokenService
      .getByValidToken(token)
      .flatMap(confirmationToken -> userService.getById(confirmationToken.getUserId()))
      .flatMap(userService::enable)
      .map(user -> ResponseEntity.ok().build())
      .cast(ResponseEntity.class)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }


}
