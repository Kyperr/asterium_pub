package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Antibiotics extends AbstractExposureItem {
	public static final String NAME = "Antibiotics";
	public static final double AMOUNT = 0.25;
	public static final String FLAV = "They solve everything.";
	public static final String IMG = "exposure.png";
	
	public Antibiotics() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
