package com.vvopaa.ega.user.confirmationtoken;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ConfirmationTokenRepository extends ReactiveMongoRepository<ConfirmationToken, String> {
  Mono<ConfirmationToken> findByToken(String confirmationToken);
}
