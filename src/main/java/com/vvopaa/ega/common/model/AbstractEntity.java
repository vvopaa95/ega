package com.vvopaa.ega.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class AbstractEntity {
  @Id
  private String id;
}
