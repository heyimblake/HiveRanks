package me.heyimblake.hiveranks.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class HiveRanksSubCommandHandler {
    private CommandSender commandSender;
    private String[] arguments;

    public HiveRanksSubCommandHandler(CommandSender commandSender, String[] arguments) {
        this.commandSender = commandSender;
        this.arguments = arguments;
    }

    /**
     * Gets the CommandSender of the subcommand.
     *
     * @return commandsender
     */
    public CommandSender getCommandSender() {
        return commandSender;
    }

    /**
     * Gets the arguments passed to the subcommand.
     *
     * @return arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * Sees if the CommandSender is a Player.
     *
     * @return true if player, false otherwise
     */
    public boolean isSenderPlayer() {
        return commandSender instanceof Player;
    }
}
