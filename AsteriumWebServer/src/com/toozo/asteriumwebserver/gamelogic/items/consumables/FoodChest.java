package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FoodChest extends AbstractFoodItem {
	public static final String NAME = "Food Chest";
	public static final int AMOUNT = 10;
	public static final String DESC = "There are " + AMOUNT + " food inside.";
	public static final String FLAV = "";
	public static final String IMG = "food.png";
	
	public FoodChest() {
		super(NAME, DESC, FLAV, IMG, AMOUNT);
	}
}
