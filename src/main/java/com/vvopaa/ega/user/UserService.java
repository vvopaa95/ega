package com.vvopaa.ega.user;

import com.vvopaa.ega.user.exception.UsernameExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserService implements ReactiveUserDetailsService {
  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userRepository.findByUsername(username).cast(UserDetails.class);
  }

  Mono<User> save(User user) {
    Function<String, Mono<User>> getErrorFallback = (username) -> Mono.error(new UsernameExistsException(username));
    return userRepository.findByUsername(user.getUsername())
      .flatMap(foundUser -> getErrorFallback.apply(foundUser.getUsername()))
      .switchIfEmpty(userRepository.save(user));
  }
}
