package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.HashSet;

import com.toozo.asteriumwebserver.gamelogic.items.equipment.Equipment;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.StatusEffect;

public class PlayerCharacter {
	// ===== CONSTANTS =====
	private static final int DEFAULT_HEALTH = 10;
	private static final int DEFAULT_STARTING_STAT = 5;
	// =====================
	
	// ===== FIELDS =====
	private String characterName = "";
	private Stats stats;
	private Inventory inventory;
	private Collection<StatusEffect> effects;
	private Equipment equipment;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public PlayerCharacter() {
		this.stats = new Stats();
		this.inventory = new Inventory();
		this.effects = new HashSet<StatusEffect>();
		this.equipment = new Equipment(this);
	}
	// ========================
	
	// ===== GETTERS =====
	public String getCharacterName() {
		return this.characterName;
	}
	
	/**
	 * @return The stats of this player after all its StatusEffects are applied.
	 */
	public Stats getEffectiveStats() {
		PlayerCharacter.Stats stats = this.getBaseStats();
		
		for (StatusEffect condition : this.getStatusEffects()) {
			stats = condition.affectStats(stats);
		}
		
		return stats;	
	}
	
	/**
	 * @return The stats of this player before any of its StatusEffects are applied.
	 */
	public Stats getBaseStats() {
		return this.stats.deepCopy();
	}
	
	public Collection<StatusEffect> getStatusEffects() {
		return this.effects;
	}
	
	/**
	 * @return this PlayerCharacter's {@link Inventory}.
	 */
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public Equipment getEquipment() {
		return this.equipment;
	}
	// ===================
	
	// ===== SETTERS =====
	public void setCharacterName(final String name) {
		this.characterName = name;
	}
	
	public void setStats(final Stats stats) {
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
	
	// ===== INNER CLASSES =====
	public static class Stats {
		// ===== FIELDS =====
		private int health = DEFAULT_HEALTH;
		private int stamina;
		private int luck;
		private int intuition;
		// ==================
		
		// ===== CONSTRUCTORS =====
		public Stats() {
			this(DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT, DEFAULT_STARTING_STAT);
		}
		
		public Stats(final int stamina, final int luck, final int intuition) {
			this(stamina, luck, intuition, DEFAULT_HEALTH);
		}
		
		public Stats(final int stamina, final int luck, final int intuition, final int health) {
			this.stamina = stamina;
			this.luck = luck;
			this.intuition = intuition;
			this.health = health;
		}
		// ========================
		
		// ===== GETTERS =====
		public int getStamina() {
			return this.stamina;
		}
		
		public int getLuck() {
			return this.luck;
		}
		
		public int getIntuition() {
			return this.intuition;
		}
		
		public int getHealth() {
			return this.health;
		}
		// ===================
		
		// ===== SETTERS =====
		public void setStamina(final int stamina) {
			this.stamina = stamina;
		}
		
		public void setLuck(final int luck) {
			this.luck = luck;
		}
		
		public void setIntuition(final int intuition) {
			this.intuition = intuition;
		}
		
		public void setHealth(final int health) {
			this.health = health;
		}
		// ===================
		
		// ===== METHODS =====
		public Stats deepCopy() {
			return new Stats(this.stamina, this.luck, this.intuition, this.health);
		}
		// ===================
	}
	// =========================
}
