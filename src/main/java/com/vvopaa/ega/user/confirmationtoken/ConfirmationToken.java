package com.vvopaa.ega.user.confirmationtoken;

import com.vvopaa.ega.common.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@Document
@NoArgsConstructor
public class ConfirmationToken extends AbstractEntity {
  private String token;
  private LocalDateTime created;
  private boolean valid;
  private String userId;

  public ConfirmationToken(String userId) {
    token = UUID.randomUUID().toString();
    created = LocalDateTime.now();
    valid = true;
    this.userId = userId;
  }
}
