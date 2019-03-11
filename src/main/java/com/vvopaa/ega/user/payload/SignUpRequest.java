package com.vvopaa.ega.user.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

  @NotBlank
  @Size(max = 40)
  private String login;

  @NotBlank
  @Size(min = 4, max = 20)
  private String password;
}
