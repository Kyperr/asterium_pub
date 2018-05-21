package com.toozo.asteriumwebserver.gamelogic;

public enum Stat {
	HEALTH (true),
	STAMINA (false),
	LUCK (false),
	INTUITION (false);
	
	private final boolean isVariable;
	
	private Stat(boolean isVariable) {
		this.isVariable = isVariable;
	}
	
	public boolean isVariable() {
		return this.isVariable;
	}
}
