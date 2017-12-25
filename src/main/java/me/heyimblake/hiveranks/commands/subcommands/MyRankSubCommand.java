package me.heyimblake.hiveranks.commands.subcommands;

import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.commands.AnnotatedHiveRanksSubCommand;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandExecutor;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandHandler;
import me.heyimblake.hiveranks.util.CachedPlayer;
import me.heyimblake.hiveranks.util.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
@HiveRanksSubCommandExecutor(subCommand = "myrank",
        syntax = "",
        description = "Displays information about your rank.",
        requiresArgumentCompletion = false,
        requiresAdminPermission = false)
public class MyRankSubCommand extends AnnotatedHiveRanksSubCommand {

    public MyRankSubCommand(HiveRanksSubCommandHandler handler) {
        super(handler);
    }

    @Override
    public boolean runPlayer() {
        Player player = ((Player) getHandler().getCommandSender());
        CachedPlayerManager cachedPlayerManager = CachedPlayerManager.getInstance();
        if (!cachedPlayerManager.isCached(player.getUniqueId())) {
            MessageUtils.sendErrorMessage(player, "You don't have a cached rank! Try logging in again.", true);
            return true;
        }
        CachedPlayer cachedPlayer = cachedPlayerManager.getCachedPlayer(player.getUniqueId());
        MessageUtils.sendVariableSuccessfulMessage(player, true, "Your HiveMC rank is %s.", cachedPlayer.getHiveRank().getColor() + cachedPlayer.getHiveRank().getHumanName() + ChatColor.GRAY);
        if (!cachedPlayer.isHiveRankActive())
            MessageUtils.sendVariableSuccessfulMessage(player, false, "However, you appear as %s.", cachedPlayer.getActiveRank().getColor() + cachedPlayer.getActiveRank().getHumanName() + ChatColor.GRAY);
        return true;
    }

    @Override
    public boolean runConsole() {
        getHandler().getCommandSender().sendMessage("You must be a player in order to perform this.");
        return true;
    }
}
