package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.Collection;
import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

/**
 * Status Effect which modifies the {@link PlayerCharacter.StatBlock} of a {@link PlayerCharacter}.
 * Encompasses a list of which stats will be modified, and a function defining how they are modified.
 * 
 * @author Greg Schmitt
 */
public class AffectStats extends AbstractStatusEffect {
	private Collection<Stat> stats;
	private Function<Integer, Integer> statModifier;
	
	/**
	 * Constructs a permanent AffectStats status effect with a default name.
	 * 
	 * @param owner The {@link PlayerCharacter} who is affected by this AffectStats.
	 * @param stats A collection of the {@link Stat}s which should be affected by this.
	 * @param statModifier A function which takes the current value of each {@link Stat}
	 * 					   in stats and returns the modified version of it (e.g. (x) -> x + 1).
	 */
	public AffectStats(PlayerCharacter owner, 
					   Collection<Stat> stats, 
					   Function<Integer, Integer> statModifier) {
		this(owner, AbstractStatusEffect.DEFAULT_NAME, 
			 stats, statModifier);
	}

	/**
	 * Constructs a permanent AffectStats status effect which has a defined name.
	 * 
	 * @param name The name of this AffectStats (e.g. "Reduced Luck").
	 */
	public AffectStats(PlayerCharacter owner, String name,
					   Collection<Stat> stats, 
					   Function<Integer, Integer> statModifier) {
		this(owner, name, AbstractStatusEffect.DEFAULT_DURATION,
			 stats, statModifier);
	}

	/**
	 * Construct an AffectStats status effect which has a defined name and duration.
	 * 
	 * @param owner The {@link PlayerCharacter} who is affected by this AffectStats.
	 * @param name The name of this AffectStats (e.g. "Reduced Luck").
	 * @param duration The duration (in turns) of this AffectStats.
	 * @param stats A collection of the {@link Stat}s which should be affected by this.
	 * @param statModifier A function which takes the current value of each {@link Stat}
	 * 					   in stats and returns the modified version of it (e.g. (x) -> x + 1).
	 */
	public AffectStats(PlayerCharacter owner, String name, int duration, 
					   Collection<Stat> stats, Function<Integer, Integer> statModifier) {
		super(owner, name, duration);
		this.stats = stats;
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
		
		// Modify the stats
		for (Stat stat : this.stats) {
			int originalStat = result.getStat(stat);
			int modifiedStat = this.statModifier.apply(originalStat);
			result.setStat(stat, modifiedStat);
		}
		
		return result;
	}
}