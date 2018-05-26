package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FoodPack extends AbstractFoodItem {
	public static final String NAME = "Food Pack";
	public static final int AMOUNT = 3;
	public static final String DESC = "There are " + AMOUNT + "food inside.";
	public static final String FLAV = "";
	public static final String IMG = "food.png";
	
	public FoodPack() {
		super(NAME, DESC, FLAV, IMG, AMOUNT);
	}
}
