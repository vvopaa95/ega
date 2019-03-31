package com.vvopaa.ega.player;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
  Mono<Long> deleteByUserId(String userId);
}
