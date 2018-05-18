package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Location {

	public enum LocationType {
		CONTROL_ROOM("control_room"),
		MED_BAY("med_bay");
		
		private final String jsonVersion;
		
		LocationType(String jsonVersion) {
			this.jsonVersion = jsonVersion;
		}
		
		public String getJSONVersion() {
			return this.jsonVersion;
		}
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
	
	public Collection<Activity> getActivities(){
		return this.activities.values();
	}
	
	public Set<String> getActivityNames(){
		return this.activities.keySet();
	}
	
	public void doActivity(String name, Game game, PlayerCharacter character) throws IllegalArgumentException{
		activities.get(name).doActivity(game, character, this);
	}
}
