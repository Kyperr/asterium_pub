package com.toozo.asteriumwebserver.gamelogic;

import java.util.List;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public interface Activity {

	public static final String SEARCH = "Search";
	public static final String USE_LOCATION_ITEM = "Use Location Item";
	public static final String REST = "Rest";
	public static final boolean VERBOSE = false;
	
	public void doActivity(Game game, PlayerCharacter character, Location location);
	
	public static Activity searchActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			if (VERBOSE) {
				System.out.printf("\tActivity says: %s performing search activity in %s.\n",
								  character.getCharacterName(), location.getName());
			}
			List<AbstractItem> loot = location.lootLocation(character);
			
			for (AbstractItem item : loot) {
				character.getInventory().add(item);
			}
		}
	};
	
	public static Activity useLocationItemActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			//TODO use a location item. differs from use item action because it is the entire turn.
		}
	};
	
	public static Activity restActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			character.rest();			
		}
	};
}
