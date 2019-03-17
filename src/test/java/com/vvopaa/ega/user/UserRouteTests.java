package com.vvopaa.ega.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRouteTests {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void testSigningUpExistingClient() {
    webTestClient.get().uri("/security/sign-up")
      .attribute("login", "vvopaa@mail.com")
      .attribute("password", "bla")
      .exchange()
      .expectStatus()
      .is4xxClientError()
      .returnResult(ResponseEntity.class);
  }

}
