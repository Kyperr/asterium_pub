package com.toozo.asteriumwebserver.gamelogic;

import java.util.List;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public interface Activity {

	public static final String SEARCH = "Search";
	public static final String USE_LOCATION_ITEM = "Use Location Item";
	public static final String LOOT_SUMMARY_MESSAGE = "You looted the %s and got %s.";
	public static final String NO_ITEMS_FOUND_PHRASE = "nothing";
	public static final String REST = "Rest";
	public static final boolean VERBOSE = false;
	
	public void doActivity(Game game, PlayerCharacter character, Location location);
	
	public static Activity searchActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			String locationName = location.getName();
			if (VERBOSE) {
				System.out.printf("\tActivity says: %s performing search activity in %s.\n",
								  character.getCharacterName(), locationName);
			}
			List<AbstractItem> loot = location.lootLocation(character);
			Inventory looterInventory = character.getInventory();
			AbstractItem item;
			
			String itemList = NO_ITEMS_FOUND_PHRASE;
			if (loot.size() > 1) {
				// Operate on first item.
				item = loot.get(0);
				itemList = item.getName();
				looterInventory.add(item);
				
				// Operate on all but first and last items.
				for (int i = 1; i < loot.size() - 1; i++) {
					item = loot.get(i);
					itemList += ", " + item.getName();
					looterInventory.add(item);
				}
				
				// Operate on last item.
				item = loot.get(loot.size() - 1);
				itemList += ", and " + item.getName();
				looterInventory.add(item);
			}
			character.addSummaryMessage(String.format(LOOT_SUMMARY_MESSAGE, locationName, itemList));
		}
	};
	
	public static Activity restActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			character.rest();			
		}
	};
}
