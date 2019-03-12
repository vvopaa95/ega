package com.vvopaa.ega.team;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TeamRepository extends ReactiveMongoRepository<Team, String> {

}
