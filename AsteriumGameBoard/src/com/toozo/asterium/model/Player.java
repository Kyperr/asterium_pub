package com.toozo.asterium.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class Player {

	private StringProperty playerName;
	
	private BooleanProperty readyStatus;
	
	public Player(String playerName) {
		this.playerName.set(playerName);
		readyStatus.set(false);
	}
	
	public void setName(String playerName) {
		this.playerName.set(playerName);
	}
	
	public void setReadyStatus(boolean readyStatus) {
		this.readyStatus.set(readyStatus);
	}
	
	public StringProperty nameProperty() {
		return playerName;
	}
	
	public BooleanProperty readyProperty() {
		return readyStatus;
	}
	
	public String getName() { 
		return playerName.get();
	}
	
	public boolean getReadyStatus() {
		return readyStatus.get();
	}
}
