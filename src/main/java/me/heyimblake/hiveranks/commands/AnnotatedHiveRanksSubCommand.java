package me.heyimblake.hiveranks.commands;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
public abstract class AnnotatedHiveRanksSubCommand {
    private HiveRanksSubCommandHandler handler;

    public AnnotatedHiveRanksSubCommand(HiveRanksSubCommandHandler handler) {
        this.handler = handler;
    }

    /**
     * The HiveRanksSubCommandHandler containing the arguments and sender.
     *
     * @return handler
     */
    public HiveRanksSubCommandHandler getHandler() {
        return handler;
    }

    /**
     * To be ran if the CommandSender is a Player.
     */
    public abstract void runPlayer();

    /**
     * To be ran if the CommandSender is not a Player.
     */
    public abstract void runConsole();
}
