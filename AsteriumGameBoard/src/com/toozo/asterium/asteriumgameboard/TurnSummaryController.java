package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TurnSummaryController extends AbstractAsteriumController{

	@FXML
	private Label label;
	private Button continueButton;
	
	public void handleContinueButtonAction(ActionEvent event) {
		// Continue the game
		try {
			getNodeNavigator().display(Display.TURN_SUMMARY);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		
	}
}
