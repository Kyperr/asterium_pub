package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

/**
 * Status Effect which can be active on a {@link PlayerCharacter}.
 * Can have any or all of a myriad of different effects,
 * including modifying stats, exposure gained, et cetera.
 * 
 * @author Greg Schmitt
 */
public abstract class StatusEffect {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	// =====================
	
	// ===== FIELDS =====
	private String name;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Construct a StatusEffect with a given name.
	 * 
	 * @param name The name of the StatusEffect.
	 */
	public StatusEffect(String name) {
		if (name != null) {
			this.name = name;
		} else {
			this.name = DEFAULT_NAME;
		}
	}
	// ========================
	
	// ===== GETTERS =====
	/**
	 * @return the name of this StatusEffect.
	 */
	public String getName() {
		return this.name;
	}
	// ===================
	
	// ===== SETTERS =====
	/**
	 * Change the name of this StatusEffect iff newName is not null.
	 * 
	 * @param newName The new name of this StatusEffect.
	 */
	public void changeName(String newName) {
		if (newName != null) {
			this.name = newName;
		}
	}
	// ===================
	
	// ===== METHODS =====
	/**
	 * Default method to affect a {@link PlayerCharacter}'s {@link PlayerCharacter.Stats}.
	 * Does not modify stats by default.
	 * 
	 * Should be overridden in the implemented StatusEffect 
	 * if the implemented StatusEffect should affect stats.
	 * 
	 * @param stats The {@link PlayerCharacter.Stats} which will be affected.
	 * @return An affected copy of stats.
	 */
	public PlayerCharacter.Stats affectStats(PlayerCharacter.Stats stats) {
		return stats.deepCopy();
	}
	
	/**
	 * Default method to affect a {@link PlayerCharacter}'s {@link PlayerCharacter.Stats}.
	 * Does not modify stats by default.
	 * 
	 * Should be overridden in the implemented StatusEffect 
	 * if the implemented StatusEffect should affect stats.
	 * 
	 * @param stats The {@link PlayerCharacter.Stats} which will be affected.
	 * @return An affected copy of stats.
	 */
	public int affectExposureGained(int unmodifiedExposureGained) {
		return unmodifiedExposureGained;
	}
	// ===================
	
}