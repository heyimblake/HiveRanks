# HiveRanks [![Build Status](https://api.travis-ci.org/heyimblake/HiveRanks.svg?branch=master)](https://travis-ci.org/heyimblake/HiveRanks)
A plugin by heyimblake.

## So what exactly is this?
* HiveRanks is a plugin to show your (or a different) [HiveMC](https://hivemc.com) rank.
* By default, the plugin will fetch your HiveMC rank and apply the colors ingame.
* You can use the different sub-commands ingame to set your or another player's colors to a different rank.
* This is entirely visual and has no usage in permissions.
* The HiveMC rank of players will update on a login after 30 minutes of the last update, or by using /hiveranks reset.
* If you use a plugin that utilizes scoreboards, the player-list and nametag colors may not work.

## How do I download it?
* You can download precompiled jars from the [releases page](https://github.com/heyimblake/HiveRanks/releases).
* Or you can use [Maven](https://maven.apache.org/) to compile this yourself. Your choice!
* Requires Java 8 and Bukkit/Spigot 1.8.8 (as of 1.1.0-MC1.8.8, the original version 1.0.0 requires Minecraft 1.9). You can use [ViaVersion](https://github.com/MylesIsCool/ViaVersion) to allow 1.9+ clients to use it.

## Statistics
* This plugin uses [bStats](https://bstats.org/plugin/bukkit/HiveRanks) to track basic server information. See their website for details to op-out.
* However, I'd REALLY encourage you to keep the statistics on. They give me information on the usage of this plugin, and they collect absolutely NO personal/identifiable information.

## Commands and Permissions
* Base Command: /hiveranks or /hr

| Sub-Command | Usage | Description | Permission |
| ----------- | ----- | ----------- | ---------- |
| set | (TargetPlayer) (RankID) | Sets the rank of a target player. | hiveranks.admin, OP, or Console |
| get | (TargetPlayer) | Gets the rank of a target player. | None |
| reset | (TargetPlayer) | Resets the display rank of a player to their Hive rank. | hiveranks.admin, OP, or Console |
| myrank | None | Displays information about your rank. | None | 

## What are the RankIDs?

| Rank | ID |
| ---- | --- |
| Regular | 0 |
| Gold | 1 |
| Diamond | 2 |
| Emerald | 3 |
| VIP | 4 |
| Moderator | 5 |
| Senior Moderator | 6 |
| Developer | 7 |
| Owner | 8 |