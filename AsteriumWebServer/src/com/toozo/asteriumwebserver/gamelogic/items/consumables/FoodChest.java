package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FoodChest extends AbstractFoodItem {
	public static final String NAME = "Food Chest";
	public static final int AMOUNT = 10;
	public static final String FLAV = "Only 20,000 calories!";
	public static final String IMG = "food.png";
	
	public FoodChest() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
