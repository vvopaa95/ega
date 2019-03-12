package com.vvopaa.ega.user.confirmationtoken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
  private final ConfirmationTokenRepository confirmationTokenRepository;

  public Mono<ConfirmationToken> saveByUserId(String userId) {
    return confirmationTokenRepository.save(new ConfirmationToken(userId));
  }

  public Mono<ConfirmationToken> getByToken(String token) {
    return confirmationTokenRepository.findByToken(token);
  }
}
