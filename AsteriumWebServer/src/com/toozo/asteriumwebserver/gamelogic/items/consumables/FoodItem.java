package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public class FoodItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String PACK_NAME = "Food Pack";
	public static final int PACK_AMOUNT = 3;

	public static final String CRATE_NAME = "Food Crate";
	public static final int CRATE_AMOUNT = 5;
	
	public static final String CHEST_NAME = "Food Chest";
	public static final int CHEST_AMOUNT = 10;
	
	public static final Map<Double, Supplier<? extends AbstractItem>> FACTORY_PROBABILITIES;
	static {
		Map<Double, Supplier<? extends AbstractItem>> probsMap = new HashMap<Double, Supplier<? extends AbstractItem>>();
		probsMap.put(0.6, FoodItem::createPack);
		probsMap.put(0.3, FoodItem::createCrate);
		probsMap.put(0.1, FoodItem::createChest);
		FACTORY_PROBABILITIES = Collections.unmodifiableMap(probsMap);
	}
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
		super(name, FACTORY_PROBABILITIES);
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
