package com.toozo.asteriumwebserver.gamelogic;

import java.util.List;
import java.util.Random;

import com.toozo.asteriumwebserver.gamelogic.Location.LocationType;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public interface Activity {

	public static final double EXPOSURE_MAX = 0.15;
	public static final double PARASITE_EXPOSURE_MIN = 0.14;
	public static final double EXPOSURE_MIN = 0.0;

	public static final String SEARCH = "Search";
	public static final String USE_LOCATION_ITEM = "Use Location Item";
	public static final String LOOT_SUMMARY_MESSAGE = "You looted the %s and got %s.";
	public static final String ITEM_GAINED_MESSAGE = "You found %s.";
	public static final String NO_ITEMS_FOUND_PHRASE = "nothing";
	public static final String EXPOSURE_GAINED_MESSAGE = "You gained %d%% exposure.";
	public static final String REST = "Rest";
	public static final boolean VERBOSE = false;

	public void doActivity(Game game, PlayerCharacter character, Location location);

	public static void addExposure(PlayerCharacter character, Location location) {
		// int luck = character.getEffectiveStats().getStat(Stat.LUCK);
		double max = EXPOSURE_MAX; // impact by luck in some way.
		double min = EXPOSURE_MIN;
		Random r = new Random();
		double rand = 0.0;
		if (location.wasVisited()) {
			min = PARASITE_EXPOSURE_MIN;
		}
		//double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		rand = min + (max - min) * r.nextDouble();
		double exp = character.getExposure() + rand;
		character.setExposure(exp);
		character.addSummaryMessage(String.format(EXPOSURE_GAINED_MESSAGE, (int) Math.floor(rand * 100)));
	}

	public static void increaseLocationExposure(Location location) {
		if (location.getType() != LocationType.CONTROL_ROOM) {
			location.setLocationVisited();
		}
	}

	public static Activity searchActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			if (VERBOSE) {
				System.out.printf("\tActivity says: %s performing search activity in %s.\n",
						character.getCharacterName(), location.getName());
			}
			List<AbstractItem> loot = location.lootLocation(game.getGameState(), character);

			for (AbstractItem item : loot) {
				character.getInventory().add(item);
				character.addSummaryMessage(String.format(ITEM_GAINED_MESSAGE, item.getName()));
			}

			if (character.isParasite()) {
				increaseLocationExposure(location);
			} else {
				addExposure(character, location);
			}
		}

	};

	public static Activity restActivity = new Activity() {
		@Override
		public void doActivity(Game game, PlayerCharacter character, Location location) {
			character.rest();
		}
	};

}
