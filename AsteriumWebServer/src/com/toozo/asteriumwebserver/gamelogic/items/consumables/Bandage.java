package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Bandage extends AbstractHealItem {
	public static final String NAME = "Bandage";
	public static final int AMOUNT = 2;
	public static final String FLAV = "";
	public static final String IMG = "heal.png";
	
	public Bandage() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
