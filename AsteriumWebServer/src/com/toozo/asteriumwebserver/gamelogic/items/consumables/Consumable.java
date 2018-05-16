package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.Item;

public abstract class Consumable extends Item {
	@Override
	public void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// TODO Use this item (and remove it from user's inventory)
	}
}