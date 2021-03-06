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
public class AbstractHealItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String DESC = "Adds %d to your health.";
	// =====================
	
	// ===== FIELDS =====
	private int healAmount;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Create a new HealItem which should heal all of its targets by healAmount.
	 * @param name The name of this HealItem.
	 * @param healAmount The amount this healItem should heal each of its targets.
	 */
	public AbstractHealItem(final String name, final String flavor, final String image, final int healAmount) {
		super(name, String.format(DESC, healAmount), flavor, image);
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
