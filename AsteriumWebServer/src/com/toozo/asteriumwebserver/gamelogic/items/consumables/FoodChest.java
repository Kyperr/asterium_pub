package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FoodChest extends AbstractFoodItem {
	public static final String NAME = "Food Chest";
	public static final int AMOUNT = 10;
	
	public FoodChest() {
		super(NAME, AMOUNT);
	}
}