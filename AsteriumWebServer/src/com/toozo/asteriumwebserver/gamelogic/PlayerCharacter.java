package com.toozo.asteriumwebserver.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AbstractStatusEffect;

public class PlayerCharacter {
	// ===== CONSTANTS =====
	private static final String DEFAULT_NAME = "DEFAULT_PC_NAME";
	private static final int REST_HEAL = 2;
	private static final double REST_EXPOSURE = 0.1;
	public static final String MULTIPLE_SUMMARY_FORMAT = "%s (x%d)";
	// =====================

	// ===== FIELDS =====
	private String characterName;
	private boolean isParasite;
	private boolean isDiscovered;
	private double exposure;
	private StatBlock stats;
	private Inventory inventory;
	private Collection<AbstractStatusEffect> effects;
	private Loadout equipment;
	private List<String> turnSummary;
	// ==================

	// ===== CONSTRUCTORS =====
	/**
	 * Constructs a PlayerCharacter with default name.
	 */
	public PlayerCharacter() {
		this(DEFAULT_NAME);
	}

	/**
	 * Constructs a PlayerCharacter with name.
	 * 
	 * @param name
	 *            The name of this PlayerCharacter.
	 */
	public PlayerCharacter(final String name) {
		this.characterName = name;
		this.isParasite = false;
		this.isDiscovered = false;
		this.exposure = 0.0;
		this.stats = new StatBlock();
		this.inventory = new Inventory();
		this.effects = new HashSet<AbstractStatusEffect>();
		this.equipment = new Loadout(this);
		this.turnSummary = new ArrayList<String>();
	}
	// ========================

	// ===== INNER CLASSES =====
	public static class StatBlock {
		// ===== CONSTANTS =====
		private static final int DEFAULT_HEALTH = 10;
		private static final int MAX_HEALTH = 10;
		private static final int DEFAULT_STARTING_STAT = 5;
		// =====================

		// ===== FIELDS =====
		Map<Stat, Integer> stats = new HashMap<>();
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
		 * @param theStat
		 *            The {@link Stat} which will be returned.
		 * @return the value of theStat.
		 */
		public int getStat(Stat theStat) {
			return this.stats.get(theStat);
		}
		// ===================

		// ===== SETTERS =====
		/**
		 * Change the value of theStat.
		 * 
		 * @param theStat
		 *            The {@link Stat} which should be changed.
		 * @param newValue
		 *            The new value of theStat.
		 */
		public void setStat(Stat theStat, int newValue) {
			if (theStat == Stat.HEALTH) {
				newValue = Math.min(newValue, MAX_HEALTH);
			}

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
	 * @return true if this PlayerCharacter is a parasite, false otherwise.
	 */
	public boolean isParasite() {
		return this.isParasite;
	}

	public boolean isDiscoveredParasite() {
		return this.isParasite() && this.isDiscovered;
	}

	/**
	 * @return This PlayerCharacter's infection percentage.
	 */
	public double getExposure() {
		return this.exposure;
	}

	/**
	 * @return A copy of this character's {@link StatBlock} before any status
	 *         effects are applied.
	 */
	public StatBlock getBaseStats() {
		return this.stats.deepCopy();
	}

	/**
	 * @return A copy of this character's {@link StatBlock} after all status effects
	 *         are applied.
	 */
	public StatBlock getEffectiveStats() {
		PlayerCharacter.StatBlock stats = this.getBaseStats();

		for (AbstractStatusEffect condition : this.getStatusEffects()) {
			stats = condition.affectStats(stats);
		}
		
		int health = stats.getStat(Stat.HEALTH);
		for (Stat stat : Stat.values()) {
			if (!stat.isVariable()) {
				stats.setStat(stat, Math.min(health, stats.getStat(stat)));
			}
		}

		return stats;
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

	public List<String> getTurnSummary() {
		return new ArrayList<String>(this.turnSummary);
	}
	// ===================

	// ===== SETTERS =====
	public void setCharacterName(final String name) {
		if (name != null) {
			this.characterName = name;
		}
	}

	/**
	 * Make this PlayerCharacter a parasite.
	 */
	public void makeParasite() {
		this.isParasite = true;
	}

	/**
	 * Set this PlayerCharacter's exposure to a new exposure value. If newExposure
	 * >= 1.0, this Player becomes a parasite.
	 * 
	 * @param newExposure
	 *            The new exposure value of this PlayerCharacter. Should be positif.
	 * @throws IllegalArgumentException
	 *             If newExposure is negative.
	 */
	public void setExposure(double newExposure) throws IllegalArgumentException {
		// Check that newInfection is positif.
		if (newExposure < 0.0) {
			throw new IllegalArgumentException();
		}

		// Set new infection
		this.exposure = newExposure;

		// Make the player a parasite if infection is at 100%.
		if (this.exposure >= 1.0) {
			this.makeParasite();
		}
	}

	/**
	 * If this PlayerCharacter is a parasite, notes that they are discovered.
	 */
	public void discover() {
		if (this.isParasite()) {
			this.isDiscovered = true;
		}
	}

	/**
	 * Adds exposureToAdd to this PlayerCharacter's exposure, after passing it
	 * through status effects. e.g. PC w/ no status effects: addExposure(0.10) ==
	 * setExposure(getExposure() + 0.10) PC w/ -50% exposure effect:
	 * addExposure(0.10) == setExposure(getExposure() + 0.05)
	 * 
	 * @param exposureToAdd
	 *            The amount of exposure (before status effects) to add.
	 */
	public void addExposure(double exposureToAdd) {
		for (AbstractStatusEffect ase : this.getStatusEffects()) {
			exposureToAdd = ase.affectExposureGained(exposureToAdd);
		}

		this.setExposure(this.getExposure() + exposureToAdd);
	}

	public void setStats(final StatBlock stats) {
		this.stats = stats.deepCopy();
	}

	/**
	 * Replaces the contents of this PlayerCharacter's {@link Inventory} with
	 * newInventory's contents.
	 * 
	 * @param newInventory
	 *            The new {@link Inventory} for this PlayerCharacter.
	 */
	public void setInventory(final Inventory newInventory) {
		this.inventory.replaceContents(newInventory);
	}

	/**
	 * Clear's this character's Turn Summary.
	 */
	public void clearSummary() {
		this.turnSummary.clear();
	}

	/**
	 * If message is non-null, adds message to this character's Turn Summary.
	 * 
	 * @param message
	 *            The new message which should be appended to the summary. Should
	 *            not be null.
	 */
	public void addSummaryMessage(String message) {
		int repeatNumber;
		int i, j;
		String oldMessage;
		boolean added = false;

		for (i = this.turnSummary.size() - 1; i >= 0; i--) {
			oldMessage = this.turnSummary.get(i);
			if (oldMessage.equals(message)) {
				// If basic message exists in list, add "message x2".
				this.turnSummary.remove(i);
				this.turnSummary.add(String.format(MULTIPLE_SUMMARY_FORMAT, message, 2));
				added = true;
			} else if (oldMessage.matches(String.format("%s \\(x([0-9]+)\\)", message))) {
				// If repeated message exists in list, add "message x[repeatNumber + 1]".

				// == Get repeatNumber ==
				// Move j to index of the x
				for (j = oldMessage.length() - 2; Character.isDigit(oldMessage.charAt(j)); j--)
					;
				// Get number from j+1 to close paren
				try {
					repeatNumber = Integer.parseInt(oldMessage.substring(j + 1, oldMessage.length() - 1));
				} catch (NumberFormatException e) {
					// Something went wrong, ignore repeated message.
					repeatNumber = 1;
				}
				// ======================

				if (repeatNumber > 1) {
					this.turnSummary.remove(i);
					this.turnSummary.add(String.format(MULTIPLE_SUMMARY_FORMAT, message, repeatNumber));
					added = true;
				}
			}
		}
		
		if (!added) {
			// Message did not exist.
			this.turnSummary.add(message);
		}
	}
	// ===================

	// ===== OTHER INSTANCE METHODS =====

	public void rest() {
		// reduce exposure
		double exp = this.getExposure();
		exp = Math.max(exp - REST_EXPOSURE, 0.0);
		this.setExposure(exp);

		// heal up
		StatBlock stats = this.getEffectiveStats();
		int health = stats.getStat(Stat.HEALTH);
		health = Math.min(StatBlock.MAX_HEALTH, health + REST_HEAL);
		stats.setStat(Stat.HEALTH, health);
	}

	/**
	 * Add a {@link AbstractStatusEffect} to the PlayerCharacter.
	 * 
	 * @param effect
	 *            the {@link AbstractStatusEffect} which should be added to the
	 *            PlayerCharacter.
	 */
	public void addStatusEffect(AbstractStatusEffect effect) {
		if (effect != null) {
			this.effects.add(effect);
		}
	}

	/**
	 * Remove a {@link AbstractStatusEffect} to the PlayerCharacter.
	 * 
	 * @param effect
	 *            the {@link AbstractStatusEffect} which should be removed from the
	 *            PlayerCharacter.
	 */
	public void removeStatusEffect(AbstractStatusEffect effect) {
		this.effects.remove(effect);
	}
	// ==================================
}
