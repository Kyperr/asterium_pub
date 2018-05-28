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
	
	private static Map<Location, Boolean> visitedByParasite = new HashMap<Location, Boolean>();

	public enum LocationType {
		CONTROL_ROOM("control_room"),
		MED_BAY("med_bay"), 
		MESS_HALL("mess_hall"), 
		RESIDENTAIL("residential"),
		ARMORY("armory"),
		LIBRARY("library"), 
		VEHICLE_BAY("vehicle_bay"),
		ENGINE_ROOM("engine_room");
		
		private final String jsonVersion;
		
		LocationType(String jsonVersion) {
			this.jsonVersion = jsonVersion;
		}
		
		public String getJSONVersion() {
			return this.jsonVersion;
		}
	}
	
	public enum LocationTier {
		TIER_0(0, 0, 2), 
		TIER_1(1, 3, 4), 
		TIER_2(2, 5, 6), 
		TIER_3(3, 7, 10);
		
		private final int tier;		
		private final int start;		
		private final int end;
		
		LocationTier(final int tier, final int start, final int end) {
			this.tier = tier;
			this.start = start;
			this.end = end;
		}
		
		public int getTier() {
			return this.tier;
		}
		
		public int getStartPosition() {
			return this.start;
		}
		
		public int getEndPosition() {
			return this.end;
		}
	}
	
	private final LocationType type;
	private final String name;
	private LootPool loot;
	private int position;
	private LocationTier tier;
	private Map<String, Activity> activities = new HashMap<String, Activity>();
	
	public Location(final String name, final LocationType type, final LootPool lootPool, final int position) {
		this.name = name;
		this.type = type; 
		this.loot = lootPool;
		this.position = position;
		for (LocationTier ltier : LocationTier.values()) {
			if (position <= ltier.getEndPosition() && position >= ltier.getStartPosition()) {
				this.tier = ltier;
				break;
			}
		}
	}
	
	public void initVisitedLocations(Collection<Location> locations) {
		for (Location loc : locations) {
			Location.visitedByParasite.put(loc, false);
		}
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final LocationType getType() {
		return this.type;
	}
	
	public final LocationTier getTier() {
		return this.tier;
	}
	
	public final int getCost() {
		return this.position + (2 * this.tier.getTier());
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
	
	public boolean wasVisited() {
		return Location.visitedByParasite.get(this);
	}

	public void doActivity(String name, Game game, PlayerCharacter character) throws IllegalArgumentException{
		if (VERBOSE) {
			System.out.printf("\tLocation says: %s performed in %s.\n", name, this.getName());
		}
		
		activities.get(name).doActivity(game, character, this);
	}
	
	public void useItem(String name, Game game, PlayerCharacter character) throws IllegalArgumentException{
		activities.get(name).doActivity(game, character, this);
	}
	
	public void setLocationVisited() {
		Location.visitedByParasite.replace(this, true);
	}
	
	public static void resetVisitedLcations() {
		for (Location loc : Location.visitedByParasite.keySet()) {
			Location.visitedByParasite.replace(loc, false);
		}
	}
	
	public List<AbstractItem> lootLocation(PlayerCharacter looter) {
		if (VERBOSE) {
			System.out.printf("\tLocation says: %s looted.\n", this.getName());
		}
		
		return loot.loot(looter);
	}
}
