package com.toozo.asteriumwebserver.gamelogic.items.garbage;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public abstract class AbstractGarbageItem extends AbstractItem {

	protected AbstractGarbageItem(final String name, final String description, final String flavor, final String image) {
		super(name, description, flavor, image, false);
	}
	
	@Override
	public void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets,
			boolean fromCommunalInventory) {
		// Cannot use!
	}
	
	@Override
	public void applyEffect(final GameState state, final PlayerCharacter user, final Collection<PlayerCharacter> targets) {
		// Do nothing!
	}
}
