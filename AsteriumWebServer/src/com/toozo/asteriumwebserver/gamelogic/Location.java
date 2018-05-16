package com.toozo.asteriumwebserver.gamelogic;

import java.util.HashMap;
import java.util.Map;

public class Location {

	public enum LocationType {
		CONTROL_ROOM, MED_BAY
	}
	
	private final LocationType type;
	private Map<String, Activity> activities = new HashMap<String, Activity>();
	
	public Location(LocationType type) {
		this.type = type; 
	}
	
	public final LocationType getType() {
		return type;
	}
	
	public void addActivity(final String name, final Activity activity) {
		this.activities.put(name, activity);
	}
	
	public void doActivity(String name, Game game, PlayerCharacter character) throws IllegalArgumentException{
		activities.get(name).doActivity(game, character, this);
	}
}
