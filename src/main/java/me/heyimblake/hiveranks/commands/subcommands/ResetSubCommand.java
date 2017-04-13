package me.heyimblake.hiveranks.commands.subcommands;

import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.HiveRanks;
import me.heyimblake.hiveranks.commands.AnnotatedHiveRanksSubCommand;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandExecutor;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandHandler;
import me.heyimblake.hiveranks.runnables.UpdateCacheRunnable;
import me.heyimblake.hiveranks.util.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
@HiveRanksSubCommandExecutor(subCommand = "reset",
        syntax = "<TargetPlayer>",
        description = "Resets the display rank of a player to their Hive rank.",
        requiresArgumentCompletion = true,
        requiresAdminPermission = true)
public class ResetSubCommand extends AnnotatedHiveRanksSubCommand {
    public ResetSubCommand(HiveRanksSubCommandHandler handler) {
        super(handler);
    }

    @Override
    public boolean runPlayer() {
        Player player = (Player) getHandler().getCommandSender();
        Player target = Bukkit.getPlayer(getHandler().getArguments()[0]);
        CachedPlayerManager cachedPlayerManager = CachedPlayerManager.getInstance();
        if (target == null) {
            MessageUtils.sendErrorMessage(player, "Sorry, that player could not be found! Either they are not online or their name was typed incorrectly.", true);
            return true;
        }
        if (!cachedPlayerManager.isCached(target.getUniqueId())) {
            MessageUtils.sendErrorMessage(player, "Hmph, the target player is online yet doesn't have a rank cached. Perhaps they should relog?", true);
            return true;
        }
        new UpdateCacheRunnable(target.getUniqueId()).runTaskAsynchronously(HiveRanks.getInstance());
        MessageUtils.sendSuccessfulMessage(player, "Resetting the rank of " + ChatColor.WHITE + target.getName() + ChatColor.GRAY + ".", true);
        return true;
    }

    @Override
    public boolean runConsole() {
        CommandSender sender = getHandler().getCommandSender();
        Player target = Bukkit.getPlayer(getHandler().getArguments()[0]);
        CachedPlayerManager cachedPlayerManager = CachedPlayerManager.getInstance();
        if (target == null) {
            sender.sendMessage("Sorry, that player could not be found! Either they are not online or their name was typed incorrectly.");
            return true;
        }
        if (!cachedPlayerManager.isCached(target.getUniqueId())) {
            sender.sendMessage("Hmph, the target player is online yet doesn't have a rank cached. Perhaps they should relog?");
            return true;
        }
        new UpdateCacheRunnable(target.getUniqueId()).runTaskAsynchronously(HiveRanks.getInstance());
        sender.sendMessage("Resetting the rank of " + target.getName() + ".");
        return true;
    }
}
