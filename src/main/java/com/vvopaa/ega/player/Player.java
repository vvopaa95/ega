package com.vvopaa.ega.player;

import com.vvopaa.ega.common.model.AbstractEntity;
import com.vvopaa.ega.player.embed.PlayerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return Objects.equals(playerInfo, player.playerInfo) &&
      Objects.equals(teamId, player.teamId) &&
      Objects.equals(userId, player.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerInfo, teamId, userId);
  }
}
