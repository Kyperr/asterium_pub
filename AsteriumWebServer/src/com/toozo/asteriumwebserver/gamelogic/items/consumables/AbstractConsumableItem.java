package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public abstract class AbstractConsumableItem extends AbstractItem {
	@Override
	public void use(GameState state, 
					PlayerCharacter user, 
					Collection<PlayerCharacter> targets,
					boolean fromCommunalInventory) {
		// TODO Use it!
	}
}
