package me.heyimblake.hiveranks.util;

import org.bukkit.ChatColor;

/**
 * https://heyimblake.me
 *
 * @author heyimblake
 * @since 3/17/2017
 */
public enum HiveRank {
    REGULAR(0, "Regular Hive Member", "Regular", ChatColor.BLUE),
    GOLD(1, "Gold Hive Member", "Gold", ChatColor.GOLD),
    DIAMOND(2, "Diamond Hive Member", "Diamond", ChatColor.AQUA),
    EMERALD(3, "Lifetime Emerald Hive Member", "Emerald", ChatColor.GREEN),
    VIP(4, "VIP Player", "VIP", ChatColor.DARK_PURPLE),
    MODERATOR(5, "Hive Moderator", "Moderator", ChatColor.RED),
    SENIOR_MODERATOR(6, "Senior Hive Moderator", "Sr.Moderator", ChatColor.DARK_RED),
    DEVELOPER(7, "Hive Developer", "Developer", ChatColor.GRAY),
    OWNER(8, "Hive Founder and Owner", "Owner", ChatColor.YELLOW);

    private int id;
    private String rankName, niceName;
    private ChatColor color;

    HiveRank(int id, String rankName, String niceName, ChatColor color) {
        this.id = id;
        this.rankName = rankName;
        this.niceName = niceName;
        this.color = color;
    }

    /**
     * The integer representation of the rank.
     *
     * @return rankid
     */
    public int getId() {
        return id;
    }

    /**
     * The name of the rank, which used on The Hive's API.
     *
     * @return rankname
     */
    public String getRankName() {
        return rankName;
    }

    /**
     * The shortened rank name that players see.
     *
     * @return nicename
     */
    public String getNiceName() {
        return niceName;
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
     * Sees if the rank is a premium rank. Excludes staff.
     *
     * @return true if premium, false otherwise
     */
    public boolean isPremium() {
        return id >= 1 && id < 5;
    }

    /**
     * Sees if the rank is a staff rank. Includes senior staff.
     *
     * @return true if staff or senior staff, false otherwise
     */
    public boolean isStaff() {
        return id >= 5;
    }

    /**
     * Sees if the rank is a senior staff rank.
     *
     * @return true if senior staff, false otherwise
     */
    public boolean isSeniorStaff() {
        return id >= 6;
    }

    /**
     * Get a HiveRank from a supplied RankID. If the RankID is invalid, it will return the Regular rank.
     *
     * @param id the id of the rank
     * @return the rank of the supplied rankid, regular if invalid
     */
    public static HiveRank getHiveRankFromID(int id) {
        for (HiveRank rank : HiveRank.values()) {
            if (rank.getId() == id)
                return rank;
        }
        return REGULAR;
    }

    /**
     * Gets a HiveRank from a supplied rank name from TheHive's API. Will return Regular rank if invalid.
     *
     * @param rankName hive rank name
     * @return rank associated with supplied name, regular if invalid
     */
    public static HiveRank getHiveRankFromName(String rankName) {
        for (HiveRank rank : HiveRank.values()) {
            if (rank.getRankName().equals(rankName))
                return rank;
        }
        return REGULAR;
    }
}
