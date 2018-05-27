package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FuelBarrel extends AbstractFuelItem {
	public static final String NAME = "Fuel Barrel";
	public static final int AMOUNT = 25;
	public static final String FLAV = "Do not shoot.";
	public static final String IMG = "fuel.png";
	
	public FuelBarrel() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
