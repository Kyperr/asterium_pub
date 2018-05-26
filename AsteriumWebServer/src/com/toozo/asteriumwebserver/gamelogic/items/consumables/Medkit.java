package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Medkit extends AbstractHealItem {
	public static final String NAME = "Medkit";
	public static final int AMOUNT = 5;
	public static final String DESC = "Adds " + AMOUNT + " to your health.";
	public static final String FLAV = "";
	public static final String IMG = "heal.png";
	
	public Medkit() {
		super(NAME, DESC, FLAV, IMG, AMOUNT);
	}
}
