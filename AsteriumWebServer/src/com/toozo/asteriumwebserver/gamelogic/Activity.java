package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public interface Activity {

	public static final String SEARCH = "search";
	public static final String USE_LOCATION_ITEM = "use_location_item";
	
	public void doActivity(Game game, PlayerCharacter character, Location location);
	
	public static Activity searchActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			Collection<AbstractItem> loot = location.lootLocation();
			
			for (AbstractItem item : loot) {
				character.getInventory().add(item);
			}
		}
	};
}
