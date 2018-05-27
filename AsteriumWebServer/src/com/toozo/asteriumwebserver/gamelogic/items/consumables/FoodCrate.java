package com.toozo.asteriumwebserver.gamelogic.items.consumables;

public class FoodCrate extends AbstractFoodItem {
	public static final String NAME = "Food Crate";
	public static final int AMOUNT = 5;
	public static final String FLAV = "Best value!";
	public static final String IMG = "food.png";
	
	public FoodCrate() {
		super(NAME, FLAV, IMG, AMOUNT);
	}
}
