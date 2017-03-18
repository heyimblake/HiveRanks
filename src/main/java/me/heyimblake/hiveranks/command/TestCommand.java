package me.heyimblake.hiveranks.command;

import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.HiveRanks;
import me.heyimblake.hiveranks.runnables.UpdateCacheRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1)
            return true;
        Player player = ((Player) sender);
        new UpdateCacheRunnable(player.getUniqueId(), Integer.parseInt(args[0])).runTaskAsynchronously(HiveRanks.getInstance());
        player.sendMessage("Input " + args[0]);
        CachedPlayerManager manager = CachedPlayerManager.getInstance();
        player.sendMessage("Active: " + manager.getCachedPlayer(player.getUniqueId()).getActiveRank().getColor() + manager.getCachedPlayer(player.getUniqueId()).getActiveRank().getNiceName());
        player.sendMessage("Hive: " + manager.getCachedPlayer(player.getUniqueId()).getHiveRank().getColor() + manager.getCachedPlayer(player.getUniqueId()).getHiveRank().getNiceName());
        return true;
    }
}
