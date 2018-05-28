package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FuelCanister extends AbstractFuelItem {
	public static final String NAME = "Fuel Canister";
	public static final int AMOUNT = 10;
	public static final String FLAV = "Streichholz und Benzinkanister";
	public static final String IMG = "fuel.png";
	
	public FuelCanister() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}