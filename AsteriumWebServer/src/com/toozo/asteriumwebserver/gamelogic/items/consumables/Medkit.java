package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class Medkit extends AbstractHealItem {
	public static final String NAME = "Medkit";
	public static final int AMOUNT = 5;
	
	public Medkit() {
		super(NAME, AMOUNT);
	}
}
