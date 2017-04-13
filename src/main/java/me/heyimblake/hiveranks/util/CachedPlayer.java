package me.heyimblake.hiveranks.util;

import com.google.gson.GsonBuilder;
import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.FileOutputStream;
import java.util.UUID;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/17/2017
 */
public class CachedPlayer {
    private UUID uuid;
    private int displayRank, hiveRank;
    private long cached;

    public CachedPlayer(UUID uuid, int displayRank, int hiveRank, long cached) {
        this.uuid = uuid;
        this.displayRank = displayRank;
        this.hiveRank = hiveRank;
        this.cached = cached;
    }

    /**
     * Gets the UUID of the cached entry.
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the time in milliseconds when the last time the entry was updated.
     *
     * @return cached
     */
    public long getCached() {
        return cached;
    }

    /**
     * Sees if the display rank is the same as the entry's hive rank.
     *
     * @return true if the display rank is the hive rank, false otherwise
     */
    public boolean isHiveRankActive() {
        return displayRank == hiveRank;
    }

    /**
     * Gets the rank that should be displayed.
     *
     * @return active rank
     */
    public HiveRank getActiveRank() {
        return isHiveRankActive() ? HiveRank.getHiveRankFromID(hiveRank) : HiveRank.getHiveRankFromID(displayRank);
    }

    /**
     * Gets the cached hive rank.
     *
     * @return hiverank
     */
    public HiveRank getHiveRank() {
        return HiveRank.getHiveRankFromID(hiveRank);
    }

    /**
     * Gets the UUID of the CachedPlayer in String format with no hyphens.
     *
     * @return uuid without hyphens
     */
    public String getUUIDNoHyphen() {
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * Updates the cache hashmap and file with the new rank information.
     *
     * @param display new display rank
     * @param hive    new hive rank
     */
    public void update(int display, int hive) {
        CachedPlayerManager manager = CachedPlayerManager.getInstance();
        boolean cached = manager.isCached(uuid);
        long currentTime = System.currentTimeMillis();
        CachedPlayer updatedCachedPlayer = new CachedPlayer(uuid, display, hive, currentTime);

        if (cached)
            manager.getUuidCachedPlayerHashMap().replace(uuid, this, updatedCachedPlayer);
        else
            manager.getUuidCachedPlayerHashMap().put(uuid, updatedCachedPlayer);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(manager.getCacheFile(uuid));
            fileOutputStream.write(new GsonBuilder().setPrettyPrinting().create().toJson(updatedCachedPlayer).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showIngameColors();
    }

    /**
     * Updates the scoreboard and display name colors of the player. (If online)
     */
    public void showIngameColors() {
        Player player = Bukkit.getServer().getPlayer(uuid);
        if (player == null)
            return;
        ScoreboardManager.getInstance().addPlayer(player);
        player.setDisplayName(CachedPlayerManager.getInstance().getCachedPlayer(uuid).getActiveRank().getColor() + player.getName() + ChatColor.RESET);
    }
}
