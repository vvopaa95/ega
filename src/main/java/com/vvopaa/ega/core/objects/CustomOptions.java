package com.vvopaa.ega.core.objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomOptions {
  private String imagePath;
  private String jwtSecret;
  private int jwtExpirationMs;
}
