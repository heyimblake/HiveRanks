package me.heyimblake.hiveranks.commands;

import java.lang.annotation.*;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/18/2017
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HiveRanksSubCommandExecutor {
    /**
     * The name of the subcommand.
     *
     * @return subcommand name
     */
    String subCommand();

    /**
     * The usage of the subcommand.
     *
     * @return subcommand syntax
     */
    String syntax();

    /**
     * Information about the subcommand.
     *
     * @return subcommand description
     */
    String description();

    /**
     * Subcommand requires that the user complete arguments.
     *
     * @return true if requires argument completion, false otherwise
     */
    boolean requiresArgumentCompletion();

    /**
     * Subcommand requires the user to have admin permission.
     *
     * @return true if must have admin permission, false otherwise.
     */
    boolean requiresAdminPermission();
}
