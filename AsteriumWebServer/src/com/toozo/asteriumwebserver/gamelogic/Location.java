package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.LootPool;

public class Location {
	public static final boolean VERBOSE = false;

	public enum LocationType {
		CONTROL_ROOM("control_room"),
		MED_BAY("med_bay"), 
		MESS_HALL("mess_hall"), 
		RESIDENTAIL("residential");
		
		private final String jsonVersion;
		
		LocationType(String jsonVersion) {
			this.jsonVersion = jsonVersion;
		}
		
		public String getJSONVersion() {
			return this.jsonVersion;
		}
	}
	
	private final LocationType type;
	private final String name;
	private LootPool loot;
	private Map<String, Activity> activities = new HashMap<String, Activity>();
	
	public Location(final String name, final LocationType type, final LootPool lootPool) {
		this.name = name;
		this.type = type; 
		this.loot = lootPool;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final LocationType getType() {
		return this.type;
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
		if (VERBOSE) {
			System.out.printf("\tLocation says: %s performed in %s.\n", name, this.getName());
		}
		
		activities.get(name).doActivity(game, character, this);
	}
	
	public List<AbstractItem> lootLocation(PlayerCharacter looter) {
		if (VERBOSE) {
			System.out.printf("\tLocation says: %s looted.\n", this.getName());
		}
		
		return loot.loot(looter);
	}
}
