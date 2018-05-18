package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.Item;

public abstract class Consumable extends Item {
	@Override
	public void use(final GameState state, final PlayerCharacter user, 
					final Collection<PlayerCharacter> targets, 
					final boolean communalInventory) {
		// TODO Use this item and remove it from the inventory
	}
}
