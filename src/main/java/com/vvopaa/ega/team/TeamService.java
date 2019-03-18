package com.vvopaa.ega.team;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TeamService {
  private final TeamRepository teamRepository;

  Mono<Team> create(Team team) {
    return teamRepository.save(team);
  }

  Flux<Team> findByNameAndCountryCode(String name, String countryCode) {
    return teamRepository.findByNameAndCountryCode(name, countryCode);
  }
}
