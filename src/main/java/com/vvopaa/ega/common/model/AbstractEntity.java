package com.vvopaa.ega.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AbstractEntity {
  @Id
  private String id;
}
