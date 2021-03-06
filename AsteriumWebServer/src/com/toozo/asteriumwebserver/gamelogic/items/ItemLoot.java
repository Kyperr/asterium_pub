package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.function.Function;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

/**
 * Structure encompassing a constructor for an {@link AbstractItem} and its probability
 * of being looted from a loot pool.
 * 
 * @author Daniel, Greg
 */
public class ItemLoot {
	// ===== FIELDS =====
	private Supplier<? extends AbstractItem> constructor;
	private int base;
	private double lukMod;
	private double intMod;
	private Function<GameState, Boolean> dropCondition;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public ItemLoot(Supplier<? extends AbstractItem> itemConstructor,
					int baseChance, 
					double luckProbabilityModifier, double intuitionProbabilityModifier,
					Function<GameState, Boolean> dropCondition) {
		this.constructor = itemConstructor;
		this.base = baseChance;
		this.lukMod = luckProbabilityModifier;
		this.intMod = intuitionProbabilityModifier;
		this.dropCondition = dropCondition;
	}
	// ========================
	
	// ===== GETTERS =====
	public int getBaseChance() {
		return this.base;
	}
	
	public int getEffectiveChance(PlayerCharacter.StatBlock stats) {
		double probability = 1.0;
		
		probability += stats.getStat(Stat.LUCK) * this.lukMod;
		probability += stats.getStat(Stat.INTUITION) * this.intMod;
		
		return (int) Math.floor(this.base * probability);
	}
	
	public AbstractItem getItem() {
		return this.constructor.get();
	}
	
	public boolean canBeDropped(GameState state) {
		return this.dropCondition.apply(state);
	}
	// ===================
}
