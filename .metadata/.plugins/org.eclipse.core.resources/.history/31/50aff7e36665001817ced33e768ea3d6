package com.toozo.asteriumwebserver.gamelogic;

import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.RescueBeacon;

/**
 * A VictoryCondition of the {@link Game}.
 * Can have a name, can be active or inactive, and contains a function which determines
 * the progress of the VictoryCondition within a {@link GameState}.
 * 
 * @author Greg Schmitt
 */
public class VictoryCondition {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "DEFAULT_VICTORY_CONDITION";
	public static final double COMPLETE_THRESHOLD = 1.0;
	public static final double ERROR_VALUE = -1.0;
	
	// ~~~~~~~ HUMAN VICTORY CONDITIONS ~~~~~~~~~~
	
	/**
	 * Function to determine whether there is a used beacon in the communal inventory.
	 * @param state The current {@link GameState}.
	 * @return COMPLETE_THRESHOLD if there is, 0.0 if there isn't.
	 */
	public static final double getBeaconProgress(GameState state) {
		for (AbstractItem item : state.getCommunalInventory()) {
			if (item.getName().equals(RescueBeacon.NAME) &&
				RescueBeacon.class.cast(item).isUsed()) {
				return COMPLETE_THRESHOLD;
			}
		}
		
		return 0.0;
	}
	
	
	// ~~~~~~ PARASITE VICTORY CONDITIONS ~~~~~~~~~
	
	/**
	 * Function to determine if the fuel has run out.
	 * @param state The current {@link GameState}.
	 * @return COMPLETE_THRESHOLD if there is, 0.0 if there isn't.
	 */
	public static final double getFuelProgress(GameState state) {
		if (state.getFuel() <= 0) {
			return COMPLETE_THRESHOLD;
		} else {
			return 0.0;
		}
	}
	
	/**
	 * Function to determine if all players are parasites.
	 * @param state The current {@link GameState}.
	 * @return COMPLETE_THRESHOLD if true, 0.0 if false.
	 */
	public static final double areAllPlayersParasites(GameState state) {
		for (PlayerCharacter pc : state.getCharacters()) {
			if (!pc.isParasite()) {
				return 0.0;
			}
		}
		
		return COMPLETE_THRESHOLD;
	}
	
	public static final double isParasiteUndiscovered(GameState state) {
		if (state.gameOver()) {
			boolean undiscoveredParasites = false;
			for (PlayerCharacter pc : state.getCharacters()) {
				undiscoveredParasites |= pc.isParasite() && pc.isDiscoveredParasite();
			}
			if (undiscoveredParasites) {
				return 1.0;
			} else {
				return 0.0;
			}
		} else {
			return 0.0;
		}
	}
	
	// =====================
	
	// ===== FIELDS =====
	private String name;
	private boolean active;
	private boolean forParasite;
	
	/*
	 * A {@link Function} which takes a GameState and 
	 * determines the progress of this VictoryCondition.
	 */
	private Function<GameState, Double> progressFunction;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Constructs a new VictoryAction.
	 * 
	 * @param name The name of this action.

	 */
	public VictoryCondition(String name, final Function<GameState, Double> progressFunction, boolean isActive, final boolean forParasite) {
		setName(name);
		setProgressFunction(progressFunction);
		setActive(isActive);
		setIsForParasite(forParasite);
	}
	
	/**
	 * Constructs an active VictoryAction.
	 * 
	 * @param name The name of this action.
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 */
	public VictoryCondition(String name, Function<GameState, Double> progressFunction, final boolean forParasite) {
		this(name, progressFunction, true, forParasite);
	}
	
	/**
	 * Constructs a nameless VictoryAction.
	 * 
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 * @param isActive Whether or not this is an active action.
	 */
	public VictoryCondition(Function<GameState, Double> progressFunction, boolean isActive, final boolean forParasite) {
		this(DEFAULT_NAME, progressFunction, isActive, forParasite);
	}
	
	/**
	 * Constructs a nameless, active VictoryAction.
	 * 
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 */
	public VictoryCondition(final Function<GameState, Double> progressFunction, final boolean forParasite) {
		this(DEFAULT_NAME, progressFunction, true, forParasite);
	}
	// ========================
	
	// ===== GETTERS =====
	/** 
	 * @return The name of this VictoryCondition, or an empty {@link String} if nameless.
	 */
	public String getName() {
		if (this.name != null) {
			return this.name;
		} else {
			return DEFAULT_NAME;
		}
	}

	/**
	 * Note: This method's computational expense is determined by the progressFunction.
	 * If this value will be reused, store it instead of calling this method repeatedly.
	 * 
	 * @param game The {@link GameState} which will determine the progress.
	 * @return The progress of this victory condition, or ERROR_VALUE if progressFunction is null.
	 */
	public double getProgress(GameState state) {
		if (this.progressFunction != null) {
			return progressFunction.apply(state);
		} else {
			return ERROR_VALUE;
		}
	}
	
	/**
	 * @return Whether this VictoryCondition is active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @return If this VictoryCondition is a parasite victory condition. 
	 */
	public boolean isForParasite() {
		return this.forParasite;
	}
	
	/**
	 * @return If this VictoryCondition is for humans.
	 */
	public boolean isForHumans() {
		return !this.isForParasite();
	}
	
	/**
	 * Note: This method's computational expense is determined by the progressFunction.
	 * If this value will be reused, store it instead of calling this method repeatedly.
	 * 
	 * @param game The {@link GameState} which will determine completion.
	 * @return True if progressFunction is not null and this 
	 * 		   VictoryCondition is complete, false otherwise.
	 */
	public boolean isComplete(GameState state) {
		return this.progressFunction != null && this.getProgress(state) >= COMPLETE_THRESHOLD;
	}
	// ===================
	
	// ===== SETTERS =====
	/**
	 * Changes the name of this VictoryCondition.
	 * @param newName The new name. If null, the name will be unchanged.
	 */
	public void setName(String newName) {
		if (newName != null) {
			this.name = newName;
		} else if (this.name == null){
			this.name = DEFAULT_NAME;
		}
	}
	
	/**
	 * Change the function which determines the progress of this VictoryCondition.
	 * @param newFunction The new progress function.
	 */
	public void setProgressFunction(Function<GameState, Double> newFunction) {
		this.progressFunction = newFunction;
	}
	
	/**
	 * Activate this VictoryCondition.
	 * Equivalent to vc.setActive(true).
	 */
	public void activate() {
		this.active = true;
	}
	
	/**
	 * Deactivate this VictoryCondition.
	 * Equivalent to vc.setActive(false).
	 */
	public void deactivate() {
		this.active = false;
	}
	
	/**
	 * Sets whether or not this VictoryCondition is active.
	 * @param isActive True if this VictoryCondition should be active, false otherwise.
	 */
	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	
	public void setIsForParasite(final boolean forParasite) {
		this.forParasite = forParasite;
	}

	/**
	 * Toggles whether this VictoryCondition is active.
	 * Equivalent to calling vc.setActive(!vc.isActive()).
	 */
	public void toggleActive() {
		this.active = !this.active;
	}
	// ===================
	
}
