package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.Item;

public abstract class Equipment extends Item {

	@Override
	public void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// TODO Equip this item.
	}

}