package com.vvopaa.ega.team;

import com.vvopaa.ega.common.model.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Document
@Data
@AllArgsConstructor
public class Team extends AuditEntity {
  private String name;
  private String shortName;
  private byte[] icon;
  private String siteHref;
  private String countryCode;
  private String city;
  private String leader; // Later Account class
}
