package com.toozo.asteriumwebserver.gamelogic.items.consumables;

import java.util.Collection;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

/**
 * A consumable item which, when consumed, heals all of its targets by some amount.
 * 
 * @author Greg Schmitt
 */
public class AbstractExposureItem extends AbstractConsumableItem {
	// ===== CONSTANTS =====
	public static final String DESC = "Reduces your exposure by %d%%.";
	// =====================
	
	// ===== FIELDS =====
	private double reductionAmount;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Create a new HealItem which should heal all of its targets by healAmount.
	 * @param name The name of this HealItem.
	 * @param healAmount The amount this healItem should heal each of its targets.
	 */
	public AbstractExposureItem(final String name, final String flavor, final String image, final double reductionAmount) {
		super(name, String.format(DESC, (int) Math.floor(reductionAmount * 100)), flavor, image);
		this.reductionAmount = reductionAmount;
	}
	// ========================

	// ===== METHODS ======
	@Override
	/**
	 * Reduces the exposure of each {@link PlayerCharacter} in targets by reductionAmount.
	 */
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// For each target of this...
		for (final PlayerCharacter target : targets) {
			target.addExposure(this.reductionAmount * -1);
		}
	}
	// ====================
}
