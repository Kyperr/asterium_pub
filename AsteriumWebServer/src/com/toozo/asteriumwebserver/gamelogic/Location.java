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
		RESIDENTAIL("residential"),
		ARMORY("armory"),
		LIBRARY("library");
		
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
		TIER_1(1, 4, 5), 
		TIER_2(2, 7, 8);
		
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
		return this.position + this.tier.getTier();
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
