package com.toozo.asteriumwebserver.gamelogic.statuseffects;

import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class ModifyExposureGained extends AbstractStatusEffect {
	private Function<Double, Double> modifier;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @param exposureModifier a function which takes original exposure and returns modified exposure.
	 */
	public ModifyExposureGained(final PlayerCharacter owner,
		  				  final Function<Double, Double> exposureModifier) {
		super(owner);
		this.initialize(exposureModifier);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @param exposureModifier a function which takes original exposure and returns modified exposure.
	 */
	public ModifyExposureGained(final PlayerCharacter owner, final String name,
		  				  final Function<Double, Double> exposureModifier) {
		super(owner, name);
		this.initialize(exposureModifier);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @param exposureModifier a function which takes original exposure and returns modified exposure.
	 */
	public ModifyExposureGained(final PlayerCharacter owner, final String name, final int duration, 
						  final Function<Double, Double> exposureModifier) {
		super(owner, name, duration);
		this.initialize(exposureModifier);
	}
	
	private void initialize(final Function<Double, Double> modifier) {
		this.modifier = modifier;
	}
	
	@Override
	public double affectExposureGained(double unmodifiedExposureGained) {
		return this.modifier.apply(unmodifiedExposureGained);
	}
}
