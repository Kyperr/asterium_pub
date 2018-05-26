package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class AbstractFoodItem extends AbstractConsumableItem {
	// ===== FIELDS =====
	private int addAmount;
	// ==================

	// ===== CONSTRUCTORS =====
	protected AbstractFoodItem(final String name, final String description, final String flavor, final String image,
			final int addAmount) {
		super(name, description, flavor, image);
		this.addAmount = addAmount;
	}
	// ========================

	// ===== METHODS ======
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setFood(state.getFood() + this.addAmount);
	}
	// ====================
}
