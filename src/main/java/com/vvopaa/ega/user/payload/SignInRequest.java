package com.vvopaa.ega.user.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
  @NotBlank
  private String usernameOrEmail;

  @NotBlank
  private String password;
}
