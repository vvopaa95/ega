package com.vvopaa.ega.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class IndexController {

  @GetMapping("/")
  public Mono<String> index() {
    log.info("IndexController is going to return index page...");
    return Mono.just("index");
  }
}
