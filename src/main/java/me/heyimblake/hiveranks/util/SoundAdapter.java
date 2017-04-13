package me.heyimblake.hiveranks.util;

import org.bukkit.Sound;

import me.heyimblake.hiveranks.HiveRanks;

public enum SoundAdapter {

	LEVEL_UP("LEVEL_UP", "ENTITY_PLAYER_LEVELUP"),
	
	NOTE_BASS("NOTE_BASS", "BLOCK_NOTE_BASS");
	//Add any sound you need NAME("1.8 name", "1.9 name");
	
	
	private String name18;
	private String name19;
	
	
	SoundAdapter(String name18, String name19){
		this.name18 = name18.toUpperCase();
		this.name19 = name19.toUpperCase();
	}
	
	
	public String getSound(){
		if(HiveRanks.getInstance().getServerVersion() >= 9 ){ //9 is for 1.9, 10 is for 1.10 and so on
			return getName19();
		}
		else{ //1.8 or lower
			return getName18();
		}
		
		
	}


	private String getName18() {
		return name18;
	}


	private String getName19() {
		return name19;
	}
	
	public Sound getBukkitSound(){
		return Sound.valueOf(getSound());
	}
	
	
}
