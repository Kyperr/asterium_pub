package com.toozo.asteriumwebserver.gamelogic;

import java.util.function.Function;

/**
 * A VictoryCondition of the {@link Game}.
 * Can have a name, can be active or inactive, and contains a function which determines
 * the progress of the VictoryCondition within a {@link GameState}.
 * 
 * @author Greg Schmitt
 */
public class VictoryCondition {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	// =====================
	
	// ===== FIELDS =====
	private String name;
	private boolean active;
	
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
	public VictoryCondition(String name, final Function<GameState, Double> progressFunction, boolean isActive) {
		setName(name);
		setProgressFunction(progressFunction);
		setActive(isActive);
	}
	
	/**
	 * Constructs an active VictoryAction.
	 * 
	 * @param name The name of this action.
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 */
	public VictoryCondition(String name, Function<GameState, Double> progressFunction) {
		this(name, progressFunction, true);
	}
	
	/**
	 * Constructs a nameless VictoryAction.
	 * 
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 * @param isActive Whether or not this is an active action.
	 */
	public VictoryCondition(Function<GameState, Double> progressFunction, boolean isActive) {
		this(DEFAULT_NAME, progressFunction, isActive);
	}
	
	/**
	 * Constructs a nameless, active VictoryAction.
	 * 
	 * @param progressFunction A function which takes a {@link GameState} 
	 * 						   and returns a progress percentage.
	 */
	public VictoryCondition(Function<GameState, Double> progressFunction) {
		this(DEFAULT_NAME, progressFunction, true);
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
	 * @return The progress of this victory condition, or -1 if progressFunction is null.
	 */
	public double getProgress(GameState game) {
		if (this.progressFunction != null) {
			return progressFunction.apply(game);
		} else {
			return -1.0;
		}
	}
	
	/**
	 * @return Whether this VictoryCondition is active.
	 */
	public boolean isActive() {
		return this.active;
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

	/**
	 * Toggles whether this VictoryCondition is active.
	 * Equivalent to calling vc.setActive(!vc.isActive()).
	 */
	public void toggleActive() {
		this.active = !this.active;
	}
	// ===================
	
}