package me.heyimblake.hiveranks.commands;

import me.heyimblake.hiveranks.commands.subcommands.GetSubCommand;
import me.heyimblake.hiveranks.commands.subcommands.MyRankSubCommand;
import me.heyimblake.hiveranks.commands.subcommands.ResetSubCommand;
import me.heyimblake.hiveranks.commands.subcommands.SetSubCommand;
import me.heyimblake.hiveranks.util.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class HiveRanksCommand implements CommandExecutor {

    private static Map<String, Class<? extends AnnotatedHiveRanksSubCommand>> subCommandClasses = new HashMap<>();
    private final String ADMIN_PERMISSION = "hiveranks.admin";

    public HiveRanksCommand() {
        registerSubCommand(MyRankSubCommand.class);
        registerSubCommand(GetSubCommand.class);
        registerSubCommand(SetSubCommand.class);
        registerSubCommand(ResetSubCommand.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            showHelpScreen(command, sender);
            return true;
        }

        String subCmdName = args[0];
        Class<? extends AnnotatedHiveRanksSubCommand> clazz = null;
        boolean isAdmin = !(sender instanceof Player) || (sender.hasPermission(ADMIN_PERMISSION) || sender.isOp());

        for (Map.Entry<String, Class<? extends AnnotatedHiveRanksSubCommand>> entry : subCommandClasses.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(subCmdName))
                clazz = entry.getValue();
        }

        if (clazz == null) {
            if (sender instanceof Player)
                MessageUtils.sendErrorMessage(((Player) sender), "Sorry, but that sub-command could not be found. Use /" + command.getName() + " to view all available sub-commands.", true);
            else
                sender.sendMessage("Sub-command not found. Use " + command.getName() + " to view all available sub-commands.");
            return true;
        }

        HiveRanksSubCommandExecutor executor = getSubCommandClassAnnotation(clazz);
        if (executor.requiresAdminPermission() && !isAdmin) {
            MessageUtils.sendErrorMessage((Player) sender, "Sorry, but you do not have sufficient permission to do this.", true);
            return true;
        }

        if (executor.requiresArgumentCompletion() && args.length < 2) {
            if (sender instanceof Player)
                MessageUtils.sendVariableErrorMessage((Player) sender, true, "Not enough arguments! Usage: %s.", "/" + command.getName() + " " + executor.subCommand() + " " + executor.syntax());
            else
                sender.sendMessage("Not enough arguments! Usage: /" + executor.subCommand() + " " + executor.syntax());
            return true;
        }

        performSubCommand(command, clazz, new HiveRanksSubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length)));
        return true;
    }

    /**
     * Prints a help screen to a CommandSender.
     *
     * @param command the base command
     * @param sender  who to send the screen to
     */
    private void showHelpScreen(Command command, CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "/" + command.getName() + " Sub-Commands:");
        for (Class<? extends AnnotatedHiveRanksSubCommand> clazz : subCommandClasses.values()) {
            HiveRanksSubCommandExecutor executor = getSubCommandClassAnnotation(clazz);
            sender.sendMessage(ChatColor.GRAY + "" + '\u25CF' + " " +
                    ChatColor.AQUA + executor.subCommand() + " " + ChatColor.DARK_AQUA + executor.syntax() + ChatColor.DARK_GRAY + " - " +
                    ChatColor.GOLD + executor.description());
        }
    }

    /**
     * Performs the sub-command and outputs a usage message if necessary.
     *
     * @param command the base command
     * @param clazz   the class extending annotatedhiverankssubcommand
     * @param handler the hiverankssubcommandhandler
     */
    private void performSubCommand(Command command, Class<? extends AnnotatedHiveRanksSubCommand> clazz, HiveRanksSubCommandHandler handler) {
        boolean result;
        try {
            Constructor constructor = clazz.getConstructor(handler.getClass());
            AnnotatedHiveRanksSubCommand subCommandInstance = (AnnotatedHiveRanksSubCommand) constructor.newInstance(handler);
            HiveRanksSubCommandExecutor annotation = getSubCommandClassAnnotation(clazz);
            if (handler.isSenderPlayer()) {
                result = subCommandInstance.runPlayer();
            } else {
                result = subCommandInstance.runConsole();
            }
            if (!result) {
                handler.getCommandSender().sendMessage(MessageUtils.getPrefix() + ChatColor.DARK_RED + "Not enough arguments! Usage: /" + command.getName() + " " + annotation.subCommand() + " " + annotation.syntax() + ".");
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a sub-command.
     *
     * @param annotatedHiveRanksSubCommand the subcommand class
     */
    private void registerSubCommand(Class<? extends AnnotatedHiveRanksSubCommand> annotatedHiveRanksSubCommand) {
        subCommandClasses.put(getSubCommandClassAnnotation(annotatedHiveRanksSubCommand).subCommand(), annotatedHiveRanksSubCommand);
    }

    /**
     * Gets the Annotation of a HiveRanksSubCommandExecutor class.
     *
     * @param clazz the hiverankssubcommandexevutor class
     * @return Annotation if it exists, null if invalid
     */
    private HiveRanksSubCommandExecutor getSubCommandClassAnnotation(Class<? extends AnnotatedHiveRanksSubCommand> clazz) {
        if (clazz.isAnnotationPresent(HiveRanksSubCommandExecutor.class)) {
            return clazz.getAnnotation(HiveRanksSubCommandExecutor.class);
        }
        return null;
    }
}
