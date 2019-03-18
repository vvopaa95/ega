package com.vvopaa.ega.team;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController("team")
@Slf4j
@AllArgsConstructor
public class TeamController {
  private final TeamService teamService;

  @PutMapping("create")
  public Mono<ResponseEntity> createTeam(@RequestBody Team team, @ApiIgnore ServerHttpRequest serverHttpRequest) {
    return Mono.just(team)
      .filter(currTeam -> currTeam.getId() == null)
      .flatMap(teamService::create)
      .map(savedTeam -> UriComponentsBuilder
        .fromHttpRequest(serverHttpRequest)
        .path("/team/{username}")
        .buildAndExpand(savedTeam.getShortName())
        .toUri())
      .map(uri -> ResponseEntity.created(uri).build())
      .defaultIfEmpty(ResponseEntity.badRequest().build())
      .cast(ResponseEntity.class);
  }

  @GetMapping("getAll")
  public Flux<Team> getTeams(
    @RequestParam("name") String name,
    @RequestParam("countryCode") String countryCode
  ) {
    return teamService
      .findByNameAndCountryCode(name,countryCode); //do LIKE %% not via MongoTemplate
  }

}
