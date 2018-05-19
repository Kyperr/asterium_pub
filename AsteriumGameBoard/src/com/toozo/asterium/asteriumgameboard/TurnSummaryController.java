package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.util.NodeNavigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TurnSummaryController {

	@FXML
	private Label label;
	private Button continueButton;
	
	public void handleContinueButtonAction(ActionEvent event) {
		// Continue the game
		NodeNavigator.loadMap();
	}
	
	@FXML
	public void initialize() {
	}
}
