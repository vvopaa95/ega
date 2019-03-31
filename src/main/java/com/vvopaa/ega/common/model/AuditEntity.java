package com.vvopaa.ega.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditEntity extends AbstractEntity {
  @CreatedDate
  private LocalDateTime created;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private LocalDateTime updated;

  @LastModifiedBy
  private String updatedBy;
}
