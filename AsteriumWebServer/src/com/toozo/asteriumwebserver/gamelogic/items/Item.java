package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class Item {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	// =====================
	
	// ===== FIELDS =====
	private String name;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected Item(String name) {
		this.name = name;
	}
	
	protected Item() {
		this.name = DEFAULT_NAME;
	}
	// ========================
	
	// ===== GETTERS =====
	public String getName() {
		if (this.name != null) {
			return this.name;
		} else {
			return DEFAULT_NAME;
		}
	}
	// ===================
	
	// ===== SETTERS =====
	/**
	 * Change the name of this Item.
	 * 
	 * @param newName The new name. If null, name will be unchanged.
	 */
	public void setName(String newName) {
		if (newName != null) {
			this.name = newName;
		} else if (this.name == null) {
			this.name = DEFAULT_NAME;
		}
	}
	// ===================
	
	// ===== METHODS =====
	/**
	 * Use this item.
	 * 
	 * Should be implemented by specific item class (e.g. Medkit) 
	 * to apply the effect of that particular item.
	 * 
	 * @param state The state of the game which may be changed by this item's effect.
	 * @param user The {@link PlayerCharacter} which is using this item.
	 * @param targets The {@link PlayerCharacter}s which this item may affects.
	 */
	public abstract void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets);
	// ===================
}