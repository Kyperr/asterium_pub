package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

/**
 * The abstract class for an {@link AbstractItem} that is consumable.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractConsumableItem extends AbstractItem {
	public static final String COMMUNAL_USE_MESSAGE = "%s was removed from the communal inventory.";
	public static final String PERSONAL_USE_MESSAGE = "You used a %s.";
	protected AbstractConsumableItem(String name, final String description, final String flavor, final String image) {
		super(name, description, flavor, image, false);
	}
	
	@Override
	public void use(final GameState state, 
					final PlayerCharacter user, 
					final Collection<PlayerCharacter> targets,
					final boolean fromCommunalInventory) {
		applyEffect(state, user, targets);
		String name = this.getName();
		if (fromCommunalInventory) {
			state.getCommunalInventory().remove(this);
			state.addSummaryMessage(String.format(COMMUNAL_USE_MESSAGE, name));
		} else {
			user.getInventory().remove(this);
			user.addSummaryMessage(String.format(PERSONAL_USE_MESSAGE, name));
		}
		
		state.syncGameBoards();
	}
	
}
