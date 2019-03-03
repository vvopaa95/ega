package com.vvopaa.ega.core;

import com.vvopaa.ega.core.objects.CustomOptions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@PropertySource("classpath:/custom.properties")
@AllArgsConstructor
public class WebSecurityConfiguration {
  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;
  private final Environment env;

  private final String[] commonUrls = {
    "/", "/*.js$","/*.ico$"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .httpBasic().disable()
      .formLogin().disable()
      .csrf().disable()
      .logout().disable()
      .authenticationManager(authenticationManager)
      .securityContextRepository(securityContextRepository)
      .authorizeExchange()
      .pathMatchers(commonUrls).permitAll()
      .anyExchange().permitAll() //later authenticated
      .and().build();
  }

  @Bean
  public CustomOptions getOptions() {
    return CustomOptions.builder()
      .imagePath(env.getProperty("images.path"))
      .jwtExpirationMs(Integer.parseInt(Objects.requireNonNull(env.getProperty("security.jwt.expirationInMs"))))
      .jwtSecret(env.getProperty("security.jwt.secret"))
      .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
