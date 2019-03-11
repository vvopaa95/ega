package com.vvopaa.ega.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Setter
public class AuditEntity extends AbstractEntity {
  @CreatedDate
  private LocalDate created;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private LocalDate updated;

  @LastModifiedBy
  private String updatedBy;
}
