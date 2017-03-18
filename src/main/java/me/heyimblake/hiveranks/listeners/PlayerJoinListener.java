package me.heyimblake.hiveranks.listeners;

import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.HiveRanks;
import me.heyimblake.hiveranks.runnables.UpdateCacheRunnable;
import me.heyimblake.hiveranks.util.CachedPlayer;
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
        CachedPlayerManager manager = CachedPlayerManager.getInstance();

        if (manager.isCached(player.getUniqueId())) {
            CachedPlayer cachedPlayer = manager.getCachedPlayer(player.getUniqueId());
            if (System.currentTimeMillis() - manager.getCachedPlayer(player.getUniqueId()).getCached() >= 7200000) {
                if (!cachedPlayer.isHiveRankActive())
                    new UpdateCacheRunnable(player.getUniqueId(), cachedPlayer.getActiveRank().getId()).runTaskAsynchronously(HiveRanks.getInstance());
                else
                    new UpdateCacheRunnable(player.getUniqueId()).runTaskAsynchronously(HiveRanks.getInstance());
                return;
            }
            cachedPlayer.showIngameColors();
        }

        new UpdateCacheRunnable(player.getUniqueId()).runTaskAsynchronously(HiveRanks.getInstance());
    }
}
