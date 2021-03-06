package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.Objects;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

/**
 * Status Effect which can be active on a {@link PlayerCharacter}.
 * Can have any or all of a myriad of different effects,
 * including modifying stats, exposure gained, et cetera.
 * 
 * @author Greg Schmitt
 */
public abstract class AbstractStatusEffect {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "DEFAULT STATUS EFFECT";
	public static final String REMOVED_MESSAGE = "You lost a status effect: %s.";
	public static final String ADDED_MESSAGE = "You gained a status effect: %s.";
	public static final int DEFAULT_DURATION = Integer.MAX_VALUE;
	// =====================
	
	// ===== FIELDS =====
	private String name;
	private int duration;
	private PlayerCharacter owner;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Construct a "permanent" StatusEffect with a default name.
	 * @param owner The {@link PlayerCharacter} who is affected by this StatusEffect.
	 */
	public AbstractStatusEffect(final PlayerCharacter owner) {
		this(owner, DEFAULT_NAME);
	}
	
	/**
	 * Construct a "permanent" StatusEffect with a given name.
	 * Permanent here means of practically infinite StatusEffect.
	 * 
	 * @param name The name of the StatusEffect.
	 */
	public AbstractStatusEffect(final PlayerCharacter owner, final String name) {
		this(owner, name, DEFAULT_DURATION);
	}
	
	/**
	 * Construct a StatusEffect with a given name and defined duration (in turns).
	 * 
	 * @param name The name of the StatusEffect. If this is null, a default name will be used.
	 * @param duration The number of turns before this StatusEffect should go away.
	 */
	public AbstractStatusEffect(final PlayerCharacter owner, final String name, final int duration) {
		this.owner = Objects.requireNonNull(owner);
		if (name != null) {
			this.name = name;
		} else {
			this.name = DEFAULT_NAME;
		}
		
		this.duration = duration;
		//this.owner.addSummaryMessage(String.format(ADDED_MESSAGE, this.getName()));
	}
	// ========================
	
	// ===== GETTERS =====
	/**
	 * @return the owner of this StatusEffect.
	 */
	public PlayerCharacter getOwner() {
		return this.owner;
	}
	
	/**
	 * @return the name of this StatusEffect.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the duration left on this StatusEffect.
	 */
	public int getDuration() {
		return this.duration;
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
	
	/**
	 * Changes the duration left on this StatusEffect.
	 * 
	 * @param newDuration the new duration.
	 */
	public void setDuration(final int newDuration) {
		this.duration = newDuration;
	}
	// ===================
	
	// ===== METHODS =====
	/**
	 * Decrements the duration of this StatusEffect, removing it if it has expired.
	 */
	public void age() {
		// Decrement duration
		this.duration--;
		
		// Remove if expired
		if (this.getDuration() < 1) {
			this.remove();
		}
	}
	
	/**
	 * Removes this StatusEffect from the {@link PlayerCharacter} to which it belongs.
	 */
	public void remove() {
		this.owner.removeStatusEffect(this);
		//cthis.owner.addSummaryMessage(String.format(REMOVED_MESSAGE, this.getName()));
	}
	
	/**
	 * Default method to affect a {@link PlayerCharacter}'s {@link PlayerCharacter.StatBlock}.
	 * Does not modify stats by default.
	 * 
	 * Should be overridden in the implemented StatusEffect 
	 * if the implemented StatusEffect should affect stats.
	 * 
	 * @param stats The {@link PlayerCharacter.StatBlock} which will be affected.
	 * @return An affected copy of stats.
	 */
	public PlayerCharacter.StatBlock affectStats(PlayerCharacter.StatBlock stats) {
		return stats.deepCopy();
	}
	
	/**
	 * Default method to affect the amount of food a {@link PlayerCharacter} consumes.
	 * Does not affect food consumed by default.
	 * 
	 * Should be overridden in the implemented StatusEffect 
	 * if the implemented StatusEffect should affect exposure gained.
	 * 
	 * @param unmodifiedFoodConsumed The amount of food would have been consumed before this took effect.
	 * @return The amount of food which should be consumed after this took effect.
	 */
	public int affectFoodUsed(int unmodifiedFoodConsumed) {
		return unmodifiedFoodConsumed;
	}
	
	/**
	 * Default method to affect the amount of exposure a {@link PlayerCharacter} gains.
	 * Does not affect exposure gained by default.
	 * 
	 * Should be overridden in the implemented StatusEffect 
	 * if the implemented StatusEffect should affect exposure gained.
	 * 
	 * @param unmodifiedExposureGained The amount of exposure would have been gained before this took effect.
	 * @return The amount of exposure which should be gained after this took effect.
	 */
	public double affectExposureGained(double unmodifiedExposureGained) {
		return unmodifiedExposureGained;
	}
	// ===================
}
