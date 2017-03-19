package me.heyimblake.hiveranks.util;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public class MessageUtils {

    private static String PREFIX = ChatColor.DARK_GRAY + "" + '\u2502' + " " + ChatColor.YELLOW + ChatColor.BOLD + "Hive" + ChatColor.RED + ChatColor.BOLD + "Ranks" + ChatColor.DARK_GRAY + "" + '\u2502' + " " + ChatColor.RESET;

    /**
     * Sends a message to a player, with optional sound, in the successful format.
     *
     * @param player    the player to send the message to
     * @param string    the message to send
     * @param playSound true if to also play a sound, false otherwise
     */
    public static void sendSuccessfulMessage(Player player, String string, boolean playSound) {
        player.sendMessage(PREFIX + ChatColor.GRAY + string);
        if (playSound)
            player.playSound(player.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2, 2);
    }

    /**
     * Sends a message, with optional sound, in the successful format with variable strings.
     *
     * @param player    the player to send the message to
     * @param playSound true if also play a sound, false otherwise
     * @param base      the base message, with "%s" formatting
     * @param variables the variables to replace "%s"
     */
    public static void sendVariableSuccessfulMessage(Player player, boolean playSound, String base, String... variables) {
        player.sendMessage(PREFIX + String.format(ChatColor.GRAY + base, variables));
        if (playSound)
            player.playSound(player.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2, 2);
    }

    /**
     * Sends a message to a player, with optional sound, in the error format.
     *
     * @param player    the player to send the message to
     * @param string    the message to send
     * @param playSound true if to also play a sound, false otherwise
     */
    public static void sendErrorMessage(Player player, String string, boolean playSound) {
        player.sendMessage(PREFIX + ChatColor.DARK_RED + string);
        if (playSound)
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BASS, 2, 1);
    }

    /**
     * Sends a message, with optional sound, in the error format with variable strings.
     *
     * @param player    the player to send the message to
     * @param playSound true if also play a sound, false otherwise
     * @param base      the base message, with "%s" formatting
     * @param variables the variables to replace "%s"
     */
    public static void sendVariableErrorMessage(Player player, boolean playSound, String base, String... variables) {
        player.sendMessage(PREFIX + String.format(ChatColor.DARK_RED + base, variables));
        if (playSound)
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BASS, 2, 2);
    }

    /**
     * Gets the chat prefix of the plugin.
     *
     * @return prefix
     */
    public static String getPrefix() {
        return PREFIX;
    }
}
