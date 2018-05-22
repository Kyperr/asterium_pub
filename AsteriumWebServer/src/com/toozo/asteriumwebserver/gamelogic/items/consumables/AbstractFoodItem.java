package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class AbstractFoodItem extends AbstractConsumableItem {
	// ===== FIELDS =====
	private int addAmount;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected AbstractFoodItem(final String name, final int addAmount) {
		super(name);
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
