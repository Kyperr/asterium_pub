package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class AffectSingleStat extends AbstractStatusEffect {
	private Stat stat;
	private Function<Integer, Integer> statModifier;
	
	public AffectSingleStat(PlayerCharacter owner, 
							Stat stat, Function<Integer, Integer> statModifier) {
		this(owner, AbstractStatusEffect.DEFAULT_NAME, 
			 stat, statModifier);
	}

	public AffectSingleStat(PlayerCharacter owner, String name,
							Stat stat, Function<Integer, Integer> statModifier) {
		this(owner, name, AbstractStatusEffect.DEFAULT_DURATION,
			 stat, statModifier);
	}

	public AffectSingleStat(PlayerCharacter owner, String name, int duration, 
							Stat stat, Function<Integer, Integer> statModifier) {
		super(owner, name, duration);
		this.stat = stat;
		this.statModifier = statModifier;
	}

	@Override	
	/**
	 * Applies the statModifier to the stat.
	 * 
	 * @param stats The {@link PlayerCharacter.StatBlock} which will be affected.
	 * @return A copy of stats with the single stat modified.
	 */
	public PlayerCharacter.StatBlock affectStats(PlayerCharacter.StatBlock stats) {
		// Copy stats
		PlayerCharacter.StatBlock result = stats.deepCopy();
		
		// Modify the stat
		int originalStat = result.getStat(this.stat);
		int modifiedStat = this.statModifier.apply(originalStat);
		result.setStat(this.stat, modifiedStat);
		
		return result;
	}
}
