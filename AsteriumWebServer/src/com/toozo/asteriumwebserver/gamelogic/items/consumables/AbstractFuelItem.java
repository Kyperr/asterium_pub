package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class AbstractFuelItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public final static String DESCRIPTION = "Adds %d fuel to the supply.";
	public final static String COMMUNAL_USE_MESSAGE = "%d fuel was added to the supply.";
	// =====================
	
	// ===== FIELDS =====
	private int addAmount;
	// ==================

	// ===== CONSTRUCTORS =====
	protected AbstractFuelItem(final String name,
							   final String flavorText, 
							   final String image,
							   final int addAmount) {
		super(name, String.format(DESCRIPTION, addAmount), flavorText, image);
		this.addAmount = addAmount;
	}
	// ========================

	// ===== METHODS ======
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setFuel(state.getFuel() + this.addAmount);
		state.addSummaryMessage(String.format(COMMUNAL_USE_MESSAGE, this.addAmount));
	}
	// ====================
}
