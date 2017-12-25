package me.heyimblake.hiveranks;

import me.heyimblake.hiveranks.util.CachedPlayer;
import me.heyimblake.hiveranks.util.HiveRank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class ScoreboardManager {
    private static ScoreboardManager instance = new ScoreboardManager();
    private Scoreboard scoreboard;
    private boolean initialized;

    private ScoreboardManager() {
        initialized = false;
    }

    public static ScoreboardManager getInstance() {
        return instance;
    }

    public void initializeScoreboard() {
        if (initialized)
            return;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Arrays.stream(HiveRank.values()).forEach(this::setupRankTeam);
        initialized = true;
    }

    private void setupRankTeam(HiveRank rank) {
        String teamName = String.valueOf(rank.getIndex());
        scoreboard.registerNewTeam(teamName);
        scoreboard.getTeam(teamName).setPrefix(String.valueOf(rank.getColor()));
        scoreboard.getTeam(teamName).setSuffix(String.valueOf(ChatColor.RESET));
    }

    public void addPlayer(Player player) {
        if (player.getScoreboard() != scoreboard)
            player.setScoreboard(scoreboard);

        CachedPlayerManager manager = CachedPlayerManager.getInstance();

        if (!manager.isCached(player.getUniqueId()))
            return;

        CachedPlayer cachedPlayer = manager.getCachedPlayer(player.getUniqueId());
        scoreboard.getTeam(String.valueOf(cachedPlayer.getActiveRank().getIndex())).addEntry(player.getName());
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
