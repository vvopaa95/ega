package com.vvopaa.ega.player;

import com.vvopaa.ega.common.model.AbstractEntity;
import com.vvopaa.ega.player.embed.PlayerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player extends AbstractEntity {
  private PlayerInfo playerInfo;
  private String teamId;
  private String userId;

  public Player(PlayerInfo playerInfo, String userId) {
    this.playerInfo = playerInfo;
    this.userId = userId;
  }
}
