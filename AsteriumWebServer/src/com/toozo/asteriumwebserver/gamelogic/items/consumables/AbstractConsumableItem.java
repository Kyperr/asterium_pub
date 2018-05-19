package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

/**
 * The abstract class for an {@link AbstractItem} that is consumable.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractConsumableItem extends AbstractItem {
	@Override
	public void use(final GameState state, 
					final PlayerCharacter user, 
					final Collection<PlayerCharacter> targets,
					final boolean fromCommunalInventory) {
		applyEffect(state, user, targets);
		if (fromCommunalInventory) {
			state.getCommunalInventory().remove(this);
		} else {
			user.getInventory().remove(this);
		}
	}
	
}