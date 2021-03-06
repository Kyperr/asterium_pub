package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.Map;
import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

/**
 * Status Effect which modifies the {@link PlayerCharacter.StatBlock} of a
 * {@link PlayerCharacter}. Encompasses a list of which stats will be modified,
 * and a function defining how they are modified.
 * 
 * @author Studio Toozo
 */
public class AffectStats extends AbstractStatusEffect {
	private Map<Stat, Function<Integer, Integer>> statModifiers;

	/**
	 * Constructs a permanent AffectStats status effect with a default name.
	 * 
	 * @param owner
	 *            The {@link PlayerCharacter} who is affected by this AffectStats.
	 * @param statModifier
	 *            A map from {@link Stat}s to a function which takes the current
	 *            value of each {@link Stat} and returns the modified version of it
	 *            (e.g. (x) -> x + 1).
	 */
	public AffectStats(PlayerCharacter owner, Map<Stat, Function<Integer, Integer>> statModifiers) {
		super(owner);
		this.initialize(statModifiers);
	}

	/**
	 * Constructs a permanent AffectStats status effect which has a defined name.
	 * 
	 * @param name
	 *            The name of this AffectStats (e.g. "Reduced Luck").
	 */
	public AffectStats(PlayerCharacter owner, String name, Map<Stat, Function<Integer, Integer>> statModifiers) {
		super(owner, name);
		this.initialize(statModifiers);
	}

	/**
	 * Construct an AffectStats status effect which has a defined name and duration.
	 * 
	 * @param owner
	 *            The {@link PlayerCharacter} who is affected by this AffectStats.
	 * @param name
	 *            The name of this AffectStats (e.g. "Reduced Luck").
	 * @param duration
	 *            The duration (in turns) of this AffectStats.
	 * @param statModifier
	 *            A map from {@link Stat}s to a function which takes the current
	 *            value of each {@link Stat} and returns the modified version of it
	 *            (e.g. (x) -> x + 1).
	 */
	public AffectStats(PlayerCharacter owner, String name, int duration,
			Map<Stat, Function<Integer, Integer>> statModifiers) {
		super(owner, name, duration);
		this.initialize(statModifiers);
	}

	private void initialize(Map<Stat, Function<Integer, Integer>> modifiers) {
		this.statModifiers = modifiers;
	}
	
	@Override
	/**
	 * Applies the statModifier to the stat.
	 * 
	 * @param stats
	 *            The {@link PlayerCharacter.StatBlock} which will be affected.
	 * @return A copy of stats with the single stat modified.
	 */
	public PlayerCharacter.StatBlock affectStats(PlayerCharacter.StatBlock stats) {
		// Copy stats
		PlayerCharacter.StatBlock result = stats.deepCopy();

		// Modify the stats
		for (Stat stat : this.statModifiers.keySet()) {
			int originalStat = result.getStat(stat);
			int modifiedStat = this.statModifiers.get(stat).apply(originalStat);
			result.setStat(stat, modifiedStat);
		}

		return result;
	}
}
