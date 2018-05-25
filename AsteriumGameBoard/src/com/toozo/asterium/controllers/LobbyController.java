package com.toozo.asterium.controllers;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller for lobby layout.
 * 
 * @author Jenna
 *
 */
public class LobbyController extends AbstractAsteriumController {

	private Node playerList;

	@FXML
	private BorderPane lobbyBorderPane;
	
	@FXML
	private Label label;

	@FXML
	private ScrollPane playersScrollPane;

	
	public void updateLobbyId() {
		try {
			label.setText("Lobby ID: " + getGameResources().getLobbyId());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void setup() {
		try {
			playerList = getNodeNavigator().getLayout(Display.PLAYER_LIST);
			playersScrollPane.setContent(playerList);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
