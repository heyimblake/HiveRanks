package me.heyimblake.hiveranks.util;

import org.bukkit.ChatColor;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/17/2017
 */
public enum HiveRank {

    REGULAR(0, "Regular", ChatColor.BLUE),
    GOLD(10, "Gold", ChatColor.GOLD),
    DIAMOND(20, "Diamond", ChatColor.AQUA),
    EMERALD(30, "Emerald", ChatColor.GREEN),
    ULTIMATE(40, "Ultimate", ChatColor.LIGHT_PURPLE),
    VIP(50,  "VIP", ChatColor.DARK_PURPLE),
    YOUTUBER(51, "YouTuber", ChatColor.DARK_PURPLE),
    STREAMER(52, "Streamer", ChatColor.DARK_PURPLE),
    CONTRIBUTOR(53, "Contributor", ChatColor.DARK_PURPLE),
    NECTAR(54, "Team Nectar", ChatColor.DARK_AQUA),
    RESERVED_STAFF(60, "Reserved Staff", ChatColor.DARK_PURPLE),
    MODERATOR(70, "Moderator", ChatColor.RED),
    SRMODERATOR(80, "Senior Moderator", ChatColor.DARK_RED),
    STAFFMANAGER(81, "Staff Manager", ChatColor.DARK_RED),
    DEVELOPER(90, "Developer", ChatColor.GRAY),
    OWNER(100, "Owner", ChatColor.YELLOW);

    private int index;
    private String human;
    private ChatColor color;

    HiveRank(int index, String human, ChatColor color) {
        this.index = index;
        this.human = human;
        this.color = color;
    }

    /**
     * Get a HiveRank from a supplied index. If the index is invalid, it will return the Regular rank.
     *
     * @param index the id of the rank
     * @return the rank of the supplied rankid, regular if invalid
     */
    public static HiveRank getHiveRankFromID(int index) {
        for (HiveRank rank : HiveRank.values()) {
            if (rank.getIndex() == index)
                return rank;
        }
        return REGULAR;
    }

    /**
     * Gets a HiveRank from a supplied rank name a human name. Will return Regular rank if invalid.
     *
     * @param rankName hive rank name
     * @return rank associated with supplied name, regular if invalid
     */
    public static HiveRank getHiveRankFromName(String rankName) {
        for (HiveRank rank : HiveRank.values()) {
            if (rank.getHumanName().equals(rankName))
                return rank;
        }
        return REGULAR;
    }

    /**
     * Gets a HiveRank from a supplied enum name. Will return Regular rank if invalid.
     *
     * @param enumName hive rank name
     * @return rank associated with supplied name, regular if invalid
     */
    public static HiveRank getHiveRankFromEnumName(String enumName) {
        for (HiveRank rank : HiveRank.values()) {
            if (rank.name().equals(enumName)) {
                return rank;
            }
        }

        return REGULAR;
    }

    /**
     * The integer representation of the rank.
     *
     * @return rankid
     */
    public int getIndex() {
        return index;
    }

    /**
     * The shortened rank name that players see.
     *
     * @return nicename
     */
    public String getHumanName() {
        return human;
    }

    /**
     * The color of the rank.
     *
     * @return color
     */
    public ChatColor getColor() {
        return color;
    }

    /**
     * Sees if the rank is a premium rank.
     *
     * @return true if premium, false otherwise
     */
    public boolean isPremium() {
        return index > REGULAR.index;
    }

    /**
     * Sees if the rank is a staff rank.
     *
     * @return true if staff or senior staff, false otherwise
     */
    public boolean isStaff() {
        return index >= RESERVED_STAFF.index;
    }

    /**
     * Sees if the rank is a senior staff rank.
     *
     * @return true if senior staff, false otherwise
     */
    public boolean isSeniorStaff() {
        return index >= SRMODERATOR.index;
    }

    public boolean isDeveloper() {
        return index >= DEVELOPER.index;
    }
}
