package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FuelCell extends AbstractFuelItem {
	public static final String NAME = "Fuel Cell";
	public static final int AMOUNT = 5;
	public static final String FLAV = "Electrochemical!";
	public static final String IMG = "fuel.png";
	
	public FuelCell() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
