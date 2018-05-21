package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

/**
 * The abstract class for an {@link AbstractItem} that is consumable.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractConsumableItem extends AbstractItem {
	protected AbstractConsumableItem(String name,
									 Map<Double, Supplier<? extends AbstractItem>> factoryProbabilities) {
		super(name, factoryProbabilities);
	}
	
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
