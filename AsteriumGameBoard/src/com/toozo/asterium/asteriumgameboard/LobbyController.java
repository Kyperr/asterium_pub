package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.toozo.asterium.model.Player;
import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import actiondata.CreateGameResponseData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import main.ClientConnectionHandler;

/**
 * Controller for lobby layout.
 * @author Jenna
 *
 */
public class LobbyController {

	private static final String fxml = "com/toozo/asterium/fxml/menu.fxml";
	
	private ClientConnectionHandler ccHandler;

	@FXML
	private ListView<Player> playerListView;
	private Label label = new Label();

	public LobbyController() {
		ccHandler = GameResources.getClientConnectionHandler();
		Platform.runLater(new Runnable() {
			@Override public void run() {
				
			}
		});
	}
	
	private void updatePlayerList() {
		// Tell ccHandler what we want to do if a player joins the lobby
		// Display all the players ????
		for (int i = 0 ; i < GameResources.getPlayers().size() ; i++) {
		    playerListView.getItems().add(GameResources.getPlayers().get(i));
		}
	}
	
	@FXML
    private void handleBackButtonAction(ActionEvent event) {
    	NodeNavigator.loadNode(fxml);
    }

	@FXML
	public void initialize() {
		playerListView.setCellFactory(lv -> new PlayerListCell());
		label.setText(GameResources.getLobbyId());
		
		//PLAYER JOIN REQUEST
//		ccHandler.registerRequestCallback(/*joinasplayer*/"", (message) -> {
//			// Add a new player to the player list
//			//This is our response data, formed from the message.
//    		JoinAsPlayerResponseData responseData = JoinAsPlayerResponseData.fromMessage(message);
//    		// Get the player information
//		});
		// START GAME REQUEST
//		ccHandler.registerRequestCallback(/*joinasplayer*/"", (message) -> {
//			
//		});
	}
	
}
