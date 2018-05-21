package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class FoodItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String PACK_NAME = "Food Pack";
	public static final int PACK_AMOUNT = 3;

	public static final String CRATE_NAME = "Food Crate";
	public static final int CRATE_AMOUNT = 5;
	
	public static final String CHEST_NAME = "Food Chest";
	public static final int CHEST_AMOUNT = 10;
	// =====================
	
	// ===== FIELDS =====
	private int addAmount;
	// ==================
	
	// ===== FACTORIES =====
	public static FoodItem createPack() {
		return new FoodItem(PACK_NAME, PACK_AMOUNT);
	}

	public static FoodItem createCrate() {
		return new FoodItem(CRATE_NAME, CRATE_AMOUNT);
	}
	
	public static FoodItem createChest() {
		return new FoodItem(CHEST_NAME, CHEST_AMOUNT);
	}
	// =====================
	
	// ===== CONSTRUCTORS =====
	public FoodItem(final String name, final int addAmount) {
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
