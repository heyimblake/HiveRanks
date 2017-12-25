package me.heyimblake.hiveranks.commands.subcommands;

import me.heyimblake.hiveranks.CachedPlayerManager;
import me.heyimblake.hiveranks.commands.AnnotatedHiveRanksSubCommand;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandExecutor;
import me.heyimblake.hiveranks.commands.HiveRanksSubCommandHandler;
import me.heyimblake.hiveranks.util.CachedPlayer;
import me.heyimblake.hiveranks.util.HiveRank;
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
@HiveRanksSubCommandExecutor(subCommand = "set",
        syntax = "<TargetPlayer> <RankID>",
        description = "Sets the rank of a target player.",
        requiresArgumentCompletion = true,
        requiresAdminPermission = true)
public class SetSubCommand extends AnnotatedHiveRanksSubCommand {

    public SetSubCommand(HiveRanksSubCommandHandler handler) {
        super(handler);
    }

    @Override
    public boolean runPlayer() {
        Player player = (Player) getHandler().getCommandSender();
        Player target = Bukkit.getPlayer(getHandler().getArguments()[0]);
        CachedPlayerManager cachedPlayerManager = CachedPlayerManager.getInstance();

        if (getHandler().getArguments().length != 2)
            return false;

        if (target == null) {
            MessageUtils.sendErrorMessage(player, "Sorry, that player could not be found! Either they are not online or their name was typed incorrectly.", true);
            return true;
        }

        if (!cachedPlayerManager.isCached(target.getUniqueId())) {
            MessageUtils.sendErrorMessage(player, "Hmph, the target player is online yet doesn't have a rank cached. Perhaps they should relog?", true);
            return true;
        }

        try {
            Integer.parseInt(getHandler().getArguments()[1]);
        } catch (Exception e) {
            MessageUtils.sendErrorMessage(player, "Ranks IDs must be a number.", true);
            return true;
        }

        CachedPlayer cachedPlayer = cachedPlayerManager.getCachedPlayer(target.getUniqueId());
        int updateRankID = Integer.parseInt(getHandler().getArguments()[1]);

        cachedPlayer.update(updateRankID, cachedPlayer.getHiveRank().getIndex());
        MessageUtils.sendVariableSuccessfulMessage(player, true, "Updated %s's visible rank to %s.", ChatColor.WHITE + target.getName() + ChatColor.GRAY, HiveRank.getHiveRankFromID(updateRankID).getColor() + HiveRank.getHiveRankFromID(updateRankID).getHumanName() + ChatColor.GRAY);

        return true;
    }

    @Override
    public boolean runConsole() {
        CommandSender sender = getHandler().getCommandSender();
        Player target = Bukkit.getPlayer(getHandler().getArguments()[0]);
        CachedPlayerManager cachedPlayerManager = CachedPlayerManager.getInstance();

        if (getHandler().getArguments().length != 2)
            return false;

        if (target == null) {
            sender.sendMessage("Sorry, that player could not be found! Either they are not online or their name was typed incorrectly.");
            return true;
        }

        if (!cachedPlayerManager.isCached(target.getUniqueId())) {
            sender.sendMessage("Hmph, the target player is online yet doesn't have a rank cached. Perhaps they should relog?");
            return true;
        }

        try {
            Integer.parseInt(getHandler().getArguments()[1]);
        } catch (Exception e) {
            sender.sendMessage("Rank IDs must be a number.");
            return true;
        }

        CachedPlayer cachedPlayer = cachedPlayerManager.getCachedPlayer(target.getUniqueId());
        int updateRankID = Integer.parseInt(getHandler().getArguments()[1]);

        cachedPlayer.update(updateRankID, cachedPlayer.getHiveRank().getIndex());
        sender.sendMessage(String.format("Updated %s's visible rank to %s.", target.getName(), HiveRank.getHiveRankFromID(updateRankID).getHumanName()));

        return true;
    }
}
