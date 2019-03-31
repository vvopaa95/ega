package com.vvopaa.ega.player;

import com.vvopaa.ega.player.embed.PlayerInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PlayerService {
  private final PlayerRepository playerRepository;

  private Mono<Player> getById(String id) {
    return playerRepository.findById(id);
  }

  public Mono<Player> updateUserInfo(PlayerInfo playerInfo, String id) {
    return getById(id)
      .doOnSuccess(player -> {
        player.setPlayerInfo(playerInfo);
        playerRepository.save(player).subscribe();
      });
  }

  public Mono<Player> save(Player player) {
    return playerRepository.save(player);
  }

  public Mono<Long> deleteByUserId(String userId) {
    return playerRepository.deleteByUserId(userId);
  }
}
