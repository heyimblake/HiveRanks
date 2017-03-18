package me.heyimblake.hiveranks.listeners;

import me.heyimblake.hiveranks.HiveRanks;
import me.heyimblake.hiveranks.runnables.UpdateCacheRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
      //  new UpdateCacheRunnable(player.getUniqueId()).runTaskAsynchronously(HiveRanks.getInstance());
    }
}
