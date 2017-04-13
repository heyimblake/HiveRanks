package me.heyimblake.hiveranks.util;

import org.bukkit.Sound;

import me.heyimblake.hiveranks.HiveRanks;

public enum ServerSound {
	LEVEL_UP("LEVEL_UP", "ENTITY_PLAYER_LEVELUP"),
	NOTE_BASS("NOTE_BASS", "BLOCK_NOTE_BASS");

	private String name18;
	private String name19;
	
	ServerSound(String name18, String name19){
		this.name18 = name18.toUpperCase();
		this.name19 = name19.toUpperCase();
	}

    /**
     * Gets the name of the current ServerSound based on the server's version.
     *
     * @return current sound version name
     */
	private String getName() {
        if (HiveRanks.getInstance().getServerVersion() >= 9) {
            return this.name19;
        }
        return this.name18;
    }

    /**
     * Gets the Sound object of the specified ServerSound.
     *
     * @return sound
     */
	public Sound getSound(){
		return Sound.valueOf(getName());
	}
}
