package com.vvopaa.ega.user.confirmationtoken;

import com.vvopaa.ega.common.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class ConfirmationToken extends AbstractEntity {
  private String confirmationToken;
  private LocalDate createdDate;
  private String userId;

  public ConfirmationToken(String userId) {
    confirmationToken = UUID.randomUUID().toString();
    createdDate = LocalDate.now();
    this.userId = userId;
  }
}
