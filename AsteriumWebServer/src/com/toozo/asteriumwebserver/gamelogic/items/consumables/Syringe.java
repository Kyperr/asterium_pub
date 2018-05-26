package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Syringe extends AbstractHealItem {
	public static final String NAME = "Nanobot Syringe";
	public static final int AMOUNT = 10;
	public static final String DESC = "Adds " + AMOUNT + "to your health.";
	public static final String FLAV = "";
	public static final String IMG = "heal.png";

	public Syringe() {
		super(NAME, DESC, FLAV, IMG, AMOUNT);
	}
}
