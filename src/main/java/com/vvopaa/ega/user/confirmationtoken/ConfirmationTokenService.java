package com.vvopaa.ega.user.confirmationtoken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
  private final ConfirmationTokenRepository confirmationTokenRepository;

  public Mono<ConfirmationToken> save(ConfirmationToken confirmationToken) {
    return confirmationTokenRepository.save(confirmationToken);
  }

  public Mono<ConfirmationToken> getByValidToken(String token) {
    return confirmationTokenRepository
      .findByTokenAndValid(token, true)
      .flatMap(confirmationToken -> {
        confirmationToken.setValid(false);
        return confirmationTokenRepository.save(confirmationToken);
      });
  }

  public Mono<Long> deleteByUserId(String userId) {
    return confirmationTokenRepository.deleteByUserId(userId);
  }

  public Mono<ConfirmationToken> getByUserId(String userId) {
    return confirmationTokenRepository.findByUserId(userId);
  }
}
