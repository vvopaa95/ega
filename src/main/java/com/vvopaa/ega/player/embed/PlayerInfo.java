package com.vvopaa.ega.player.embed;

import com.vvopaa.ega.user.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfo {
  private String firstName;
  private String lastName;
  private GenderEnum gender;
  private LocalDate dateOfBirth;
  private String country;
}
