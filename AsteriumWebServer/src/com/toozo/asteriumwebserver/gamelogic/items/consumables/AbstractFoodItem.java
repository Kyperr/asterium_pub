package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class AbstractFoodItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String DESC = "Adds %d food to the supply.";
	public static final String COMMUNAL_USE_MESSAGE = "%d food was added to the supply.";
	// =====================
	
	// ===== FIELDS =====
	private int addAmount;
	// ==================

	// ===== CONSTRUCTORS =====
	protected AbstractFoodItem(final String name, final String flavor, final String image,
			final int addAmount) {
		super(name, String.format(DESC, addAmount), flavor, image);
		this.addAmount = addAmount;
	}
	// ========================

	// ===== METHODS ======
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setFood(state.getFood() + this.addAmount);
		state.addSummaryMessage(String.format(COMMUNAL_USE_MESSAGE, this.addAmount));
	}
	// ====================
}
