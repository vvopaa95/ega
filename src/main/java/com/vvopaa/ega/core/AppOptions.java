package com.vvopaa.ega.core;

import com.vvopaa.ega.core.objects.CustomOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("classpath:/custom.properties")
public class AppOptions {
  private final Environment env;

  @Autowired
  public AppOptions(Environment env) {
    this.env = env;
  }

  @Bean
  public CustomOptions getOptions() {
    return CustomOptions.builder()
      .imagePath(env.getProperty("images.path"))
      .jwtExpirationMs(Integer.parseInt(Objects.requireNonNull(env.getProperty("security.jwt.expirationInMs"))))
      .jwtSecret(env.getProperty("security.jwt.secret"))
      .build();
  }
}
