package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class RescueBeacon extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Rescue Beacon";

	/*public static final Map<Supplier<? extends AbstractItem>, Double> FACTORY_PROBABILITIES;
	static {
		Map<Supplier<? extends AbstractItem>, Double> probsMap = new HashMap<Supplier<? extends AbstractItem>, Double>();
		probsMap.put(Book::createBook, 1.0);
		FACTORY_PROBABILITIES = Collections.unmodifiableMap(probsMap);
	}*/
	// =====================
	
	// ===== FIELDS =====
	private boolean used = true;
	// ==================
	
	// ====== CONSTRUCTORS =====
	public RescueBeacon() {
		super(NAME);
		
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
