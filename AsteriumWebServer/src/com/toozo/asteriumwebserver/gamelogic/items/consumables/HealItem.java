package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

/**
 * A consumable item which, when consumed, heals all of its targets by some amount.
 * 
 * @author Greg Schmitt
 */
public class HealItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String BANDAGE_NAME = "Bandage";
	public static final int BANDAGE_HEAL_AMOUNT = 2;
	
	public static final String MEDKIT_NAME = "Medkit";
	public static final int MEDKIT_HEAL_AMOUNT = 5;
	
	public static final String TRIAGE_NAME = "Nanobot Syringe";
	public static final int TRIAGE_HEAL_AMOUNT = 10;
	// =====================
	
	// ===== FIELDS =====
	private int healAmount;
	// ==================

	// ===== FACTORIES =====
	public static HealItem createBandage() {
		return new HealItem(BANDAGE_NAME, BANDAGE_HEAL_AMOUNT);
	}
	
	public static HealItem createMedkit() {
		return new HealItem(MEDKIT_NAME, MEDKIT_HEAL_AMOUNT);
	}
	
	public static HealItem createTriage() {
		return new HealItem(TRIAGE_NAME, TRIAGE_HEAL_AMOUNT);
	}
	// =====================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Create a new HealItem which should heal all of its targets by healAmount.
	 * @param name The name of this HealItem.
	 * @param healAmount The amount this healItem should heal each of its targets.
	 */
	public HealItem(final String name, final int healAmount) {
		super(name);
		this.healAmount = healAmount;
	}
	// ========================

	// ===== METHODS ======
	@Override
	/**
	 * Add the defined healAmount to each {@link PlayerCharacter} in targets.
	 */
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// For each target of this...
		for (final PlayerCharacter target : targets) {
			// Get copy of current stats
			PlayerCharacter.StatBlock stats = target.getBaseStats();
			
			// Modify health stat
			int oldHealth = stats.getStat(Stat.HEALTH);
			int newHealth = oldHealth + healAmount;
			stats.setStat(Stat.HEALTH, newHealth);
			target.setStats(stats);
		}
	}
	// ====================
}