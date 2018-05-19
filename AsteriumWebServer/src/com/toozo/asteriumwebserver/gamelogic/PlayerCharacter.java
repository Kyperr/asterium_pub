package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AbstractStatusEffect;

public class PlayerCharacter {
	// ===== CONSTANTS =====
	private static final int DEFAULT_HEALTH = 10;
	private static final int DEFAULT_STARTING_STAT = 5;
	private static final String DEFAULT_NAME = "";
	// =====================
	
	// ===== FIELDS =====
	private String characterName;
	private StatBlock stats;
	private Inventory inventory;
	private Collection<AbstractStatusEffect> effects;
	private Loadout equipment;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public PlayerCharacter() {
		this.characterName = DEFAULT_NAME;
		this.stats = new StatBlock();
		this.inventory = new Inventory();
		this.effects = new HashSet<AbstractStatusEffect>();
		this.equipment = new Loadout(this);
	}
	// ========================
	
	// ===== INNER CLASSES =====
	public static class StatBlock {
		// ===== FIELDS =====
		Map<Stat, Integer> stats = new HashMap<>();;
		// ==================
		
		// ===== CONSTRUCTORS =====
		public StatBlock() {
			this(DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT);
		}
		
		public StatBlock(final int stamina, final int luck, final int intuition) {
			this(stamina, luck, intuition, DEFAULT_HEALTH);
		}
		
		public StatBlock(final int stamina, final int luck, final int intuition, final int health) {
			this.stats.put(Stat.STAMINA, stamina);
			this.stats.put(Stat.LUCK, luck);
			this.stats.put(Stat.INTUITION, intuition);
			this.stats.put(Stat.HEALTH, health);
		}
		// ========================
		
		// ===== GETTERS =====
		/**
		 * @param theStat The {@link Stat} which will be returned.
		 * @return the value of theStat.
		 */
		public int getStat(Stat theStat) {
			return this.stats.get(theStat);
		}
		// ===================
		
		// ===== SETTERS =====
		/**
		 * Change the value of theStat.
		 * @param theStat The {@link Stat} which should be changed.
		 * @param newValue The new value of theStat.
		 */
		public void setStat(Stat theStat, int newValue) {
			this.stats.put(theStat, newValue);
		}
		// ===================
		
		// ===== METHODS =====
		/**
		 * @return a deep copy of this Stats.
		 */
		public StatBlock deepCopy() {
			StatBlock copy = new StatBlock();
			
			for (Stat stat : Stat.values()) {
				copy.stats.put(stat, this.getStat(stat));
			}
			
			return copy;
		}
		// ===================
	}
	// =========================
	
	// ===== GETTERS =====
	public String getCharacterName() {
		return this.characterName;
	}
	
	/**
	 * @return The stats of this player after all its StatusEffects are applied.
	 */
	public StatBlock getEffectiveStats() {
		PlayerCharacter.StatBlock stats = this.getBaseStats();
		
		for (AbstractStatusEffect condition : this.getStatusEffects()) {
			stats = condition.affectStats(stats);
		}
		
		return stats;	
	}
	
	/**
	 * @return The stats of this player before any of its StatusEffects are applied.
	 */
	public StatBlock getBaseStats() {
		return this.stats.deepCopy();
	}
	
	public Collection<AbstractStatusEffect> getStatusEffects() {
		return this.effects;
	}
	
	/**
	 * @return this PlayerCharacter's {@link Inventory}.
	 */
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public Loadout getEquipment() {
		return this.equipment;
	}
	// ===================
	
	// ===== SETTERS =====
	public void setCharacterName(final String name) {
		if (name != null) {
			this.characterName = name;
		}
	}
	
	public void setStats(final StatBlock stats) {
		this.stats = stats;
	}
	
	/**
	 * Replaces the contents of this PlayerCharacter's 
	 * {@link Inventory} with newInventory's contents.
	 * 
	 * @param newInventory The new {@link Inventory} for this PlayerCharacter.
	 */
	public void setInventory(final Inventory newInventory) {
		this.inventory.replaceContents(newInventory);
	}
	// ===================
	
	// ===== OTHER INSTANCE METHODS =====
	/**
	 * Add a {@link AbstractStatusEffect} to the PlayerCharacter.
	 * 
	 * @param effect the {@link AbstractStatusEffect} which should be added to the PlayerCharacter.
	 */
	public void addStatusEffect(AbstractStatusEffect effect) {
		if (effect != null) {
			this.effects.add(effect);
		}
	}
	
	/**
	 * Remove a {@link AbstractStatusEffect} to the PlayerCharacter.
	 * 
	 * @param effect the {@link AbstractStatusEffect} which should be removed from the PlayerCharacter.
	 */
	public void removeStatusEffect(AbstractStatusEffect effect) {
		this.effects.remove(effect);
	}
	// ==================================
}
