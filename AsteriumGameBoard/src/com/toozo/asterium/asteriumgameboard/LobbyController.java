package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for lobby layout.
 * @author Jenna
 *
 */
public class LobbyController {

	private static final String fxml = "com/toozo/asterium/fxml/menu.fxml";
	
	@FXML
	private Label label = new Label();

	private void updatePlayerList() {
		// Tell ccHandler what we want to do if a player joins the lobby
		// Display all the players ????
		/*
		for (int i = 0 ; i < GameResources.getPlayers().size() ; i++) {
		    playerListView.getItems().add(new PlayerListCell(GameResources.getPlayers().get(i)));
		}
		*/
	}
	
	@FXML
    private void handleBackButtonAction(ActionEvent event) {
    	NodeNavigator.loadNode(fxml);
    }
	
	@FXML
	private void handleReadyButtonAction(ActionEvent event) {
		NodeNavigator.loadMap();
	}

	@FXML
	public void initialize() {
	}
	
	public void update() {
		label.setText(GameResources.getLobbyId());
	}
	
}
