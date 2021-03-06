package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class LowerHealth extends AffectStats {
	public static final String NAME = "Health Lowered";
	
	private boolean becauseStarving;
	
	public LowerHealth(PlayerCharacter owner, int amount, boolean becauseStarving) {
		super(owner, NAME, getLowerHealthMap(amount));
		this.becauseStarving = becauseStarving;
	}

	public LowerHealth(PlayerCharacter owner, int duration, int amount, boolean becauseStarving) {
		super(owner, NAME, duration, getLowerHealthMap(amount));
		this.becauseStarving = becauseStarving;
	}
	
	public boolean becauseStarving() {
		return this.becauseStarving;
	}
	
	private static Map<Stat, Function<Integer, Integer>> getLowerHealthMap(int amount) {
		Map<Stat, Function<Integer, Integer>> result = new HashMap<Stat, Function<Integer, Integer>>();
		result.put(Stat.HEALTH, (x) -> (x - amount));
		return result;
	}
}
