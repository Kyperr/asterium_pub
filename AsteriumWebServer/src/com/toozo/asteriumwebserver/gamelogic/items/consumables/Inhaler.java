package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Inhaler extends AbstractExposureItem {
	public static final String NAME = "Anti-exposure Inhaler";
	public static final double AMOUNT = 0.10;
	public static final String FLAV = "You'll get bullied for using this.";
	public static final String IMG = "exposure.png";
	
	public Inhaler() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
