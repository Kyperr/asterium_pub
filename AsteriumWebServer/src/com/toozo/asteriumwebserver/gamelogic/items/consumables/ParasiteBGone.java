package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class ParasiteBGone extends AbstractExposureItem {
	public static final String NAME = "Parasite-B-Gone";
	public static final double AMOUNT = 1.0;
	public static final String FLAV = "Or your money back!";
	public static final String IMG = "exposure.png";
	
	public ParasiteBGone() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
