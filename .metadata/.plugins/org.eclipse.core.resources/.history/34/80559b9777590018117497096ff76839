package com.toozo.asteriumwebserver.gamelogic;

import java.util.function.Function;

public abstract class StatusEffect {
	private Function<PlayerCharacter, PlayerCharacter> effect;
	
	public StatusEffect(Function<PlayerCharacter, PlayerCharacter> statusEffect) {
		this.effect = statusEffect;
	}
	
	public PlayerCharacter apply(PlayerCharacter character) {
		return effect.apply(character);
	}
}
