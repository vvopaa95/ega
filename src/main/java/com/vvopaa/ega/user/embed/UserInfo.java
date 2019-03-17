package com.vvopaa.ega.user.embed;

import com.vvopaa.ega.user.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
  private String firstName;
  private String lastName;
  private GenderEnum gender;
  private LocalDate dateOfBirth;
  private String country;
}
