package me.heyimblake.hiveranks;

import me.heyimblake.hiveranks.commands.HiveRanksCommand;
import me.heyimblake.hiveranks.listeners.PlayerJoinListener;
import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class HiveRanks extends JavaPlugin {
    private static HiveRanks instance;
    private Metrics metrics;
    private final int SERVER_VERSION = Bukkit.getVersion().contains("1.8") ? 8 : 9;

    /**
     * Gets the instance of this class.
     *
     * @return instance of main class
     */
    public static HiveRanks getInstance() {
        return instance;
    }

    public int getServerVersion(){
    	return SERVER_VERSION;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = Bukkit.getPluginManager();

        CachedPlayerManager.getInstance().initializeDirectory();
        ScoreboardManager.getInstance().initializeScoreboard();
        
        metrics = new Metrics(this);
        
        getLogger().log(Level.INFO, "Detected server version: " + Bukkit.getVersion() + ". Features will be adjusted for this version.");

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        getCommand("hiveranks").setExecutor(new HiveRanksCommand());
    }

    @Override
    public void onDisable() {

    }

    /**
     * Gets the Metrics object.
     *
     * @return metrics
     */
    public Metrics getMetrics() {
        return metrics;
    }
}
