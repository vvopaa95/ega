package com.vvopaa.ega.team;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TeamRepository extends ReactiveMongoRepository<Team, String> {
  Flux<Team> findByNameAndCountryCode(String name, String countryCode);
}
