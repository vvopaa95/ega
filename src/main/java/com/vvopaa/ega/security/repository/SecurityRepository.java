package com.vvopaa.ega.security.repository;

import com.vvopaa.ega.security.model.SecurityUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SecurityRepository extends ReactiveMongoRepository<SecurityUser, String> {
  Mono<SecurityUser> findByUsername(String username);
}
