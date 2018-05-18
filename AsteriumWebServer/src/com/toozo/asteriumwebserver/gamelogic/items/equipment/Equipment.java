package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.Item;

public abstract class Equipment extends Item {

	@Override
	public void use(final GameState state, final PlayerCharacter user, 
			final Collection<PlayerCharacter> targets, 
			final boolean communalInventory) {
		// TODO Equip this item and move it from the inventory to the user's equipment
		// TODO Apply affect of item to user
		
	}

}
