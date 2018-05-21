package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public abstract class AbstractLocationItem extends AbstractItem {
	protected AbstractLocationItem(final String name) {
		super(name);
	}
	
	@Override
	public void use(GameState state, 
					PlayerCharacter user, 
					Collection<PlayerCharacter> targets,
					boolean fromCommunalInventory) {
		applyEffect(state, user, targets);
		if (fromCommunalInventory) {
			state.getCommunalInventory().remove(this);
		} else {
			user.getInventory().remove(this);
		}
	}
}