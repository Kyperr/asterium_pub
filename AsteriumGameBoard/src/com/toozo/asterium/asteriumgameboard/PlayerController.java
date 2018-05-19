package com.toozo.asterium.asteriumgameboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerController {

	private static final String READY = "Ready";
	private static final String NOT_READY = "Not Ready";
	
	@FXML
	private Label nameLabel;
	
	@FXML
	private Label readyLabel;
	
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	public void setReadyStatus(boolean status) {
		if (status) {
			readyLabel.setText(READY);
		} else {
			readyLabel.setText(NOT_READY);
		}
	}
}
