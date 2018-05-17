package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class StatusEffect {
	private Function<PlayerCharacter, PlayerCharacter> effect;
	
	public StatusEffect(Function<PlayerCharacter, PlayerCharacter> statusEffect) {
		this.effect = statusEffect;
	}
	
	public PlayerCharacter apply(PlayerCharacter character) {
		return effect.apply(character);
	}
}
