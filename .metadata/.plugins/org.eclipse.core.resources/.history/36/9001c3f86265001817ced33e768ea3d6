package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location.LocationType;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public abstract class AbstractLocationItem extends AbstractItem {
	public static final String USE_MESSAGE = "You used %s.";
	private Collection<LocationType> useLocations;

	protected AbstractLocationItem(final String name, final String description, final String flavor, final String image,
			final Collection<LocationType> useLocations) {
		super(name, description, flavor, image, true);
		this.useLocations = useLocations;
	}

	public Collection<LocationType> getUseLocations() {
		return this.useLocations;
	}
	
	public void setUseLocations(final Collection<LocationType> useLocations) {
		this.useLocations = useLocations;
	}
	
	@Override
	public void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets,
			boolean fromCommunalInventory) {
		applyEffect(state, user, targets);
		
		user.getInventory().remove(this);
		user.addSummaryMessage(String.format(USE_MESSAGE, this.getName()));
	}
}
