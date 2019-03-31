package com.vvopaa.ega.team;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TeamRepository extends ReactiveMongoRepository<Team, String> {
  @Query(value = "{'name': {$regex : ?0, $options: 'i'}, 'countryCode': {$regex : ?1, $options: 'i'}}")
  Flux<Team> findByNameAndCountryCode(String name, String countryCode);
}
