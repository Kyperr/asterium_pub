package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public class RescueBeacon extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Rescue Beacon";

	public static final Map<Double, Supplier<? extends AbstractItem>> FACTORY_PROBABILITIES;
	static {
		Map<Double, Supplier<? extends AbstractItem>> probsMap = new HashMap<Double, Supplier<? extends AbstractItem>>();
		probsMap.put(1.0, Book::createBook);
		FACTORY_PROBABILITIES = Collections.unmodifiableMap(probsMap);
	}
	// =====================
	
	// ===== FIELDS =====
	private boolean used = true;
	// ==================
	
	// ====== CONSTRUCTORS =====
	public RescueBeacon() {
		super(NAME, FACTORY_PROBABILITIES);
		
	}
	// =========================

	// ===== GETTERS =====
	public boolean isUsed() {
		return this.used;
	}
	// ===================
	
	// ===== SETTERS =====
	public void setUsed(final boolean isUsed) {
		this.used = isUsed;
	}
	// ===================
	
	// ===== METHODS =====
	@Override
	public void use(GameState state, 
					PlayerCharacter user, 
					Collection<PlayerCharacter> targets,
					boolean fromCommunalInventory) {
		applyEffect(state, user, targets);
		
		// Move from player inventory to communal inventory if not already there
		if (!fromCommunalInventory) {
			user.getInventory().remove(this);
			state.addCommunalItem(this);
		}
	}
	
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		this.setUsed(true);
	}
	// ====================

}
