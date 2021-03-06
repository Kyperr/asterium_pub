package com.toozo.asterium.controllers;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator.Display;

import actiondata.ActionData;
import actiondata.CreateGameResponseData;
import actiondata.GeneralRequestData;
import actiondata.JoinAsGameBoardRequestData;
import actiondata.JoinAsGameBoardRequestData.GameBoardData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import message.Request;

/**
 * Controller for main menu layout.
 * 
 * @author Jenna
 *
 */
public class MenuController extends AbstractAsteriumController {

	@FXML
	private Label label;

	@FXML
	private Button lobbyIdButton;

	@FXML
	private Button backButton;

	@FXML
	private void handleLobbyIdButtonAction(ActionEvent event) {

		GeneralRequestData cgrData = new GeneralRequestData(ActionData.CREATE_GAME);

		Request request = new Request(cgrData, "");

		// Here, we say that once we get the message back, run the lambda...
		try {
			getGameResources().getClientConnectionHandler().send(request.jsonify().toString(), (message) -> {

				// ... and now that we have a message response from the server, we need to set
				// the text
				// of the label, however, that needs to be done on the UI thread, so we use
				// Platform.runLater(Runnable) to "send" our action to the correct thread :)
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						// Once we have create a game, we need to join it.

						// This is our response data, formed from the message.
						CreateGameResponseData responseData = CreateGameResponseData.fromMessage(message);

						try {
							getGameResources().setAuthToken(responseData.getAuthToken());

							joinLobby(responseData.getLobbyID());

							// Save the lobby id
							getGameResources().setLobbyId(responseData.getLobbyID());
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			});
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void joinLobby(String lobbyID) {

		GameBoardData gameData = new GameBoardData();

		JoinAsGameBoardRequestData data = new JoinAsGameBoardRequestData(lobbyID, gameData);

		try {
			Request request = new Request(data, getGameResources().getAuthToken());

			getGameResources().getClientConnectionHandler().send(request.jsonify().toString(), (message) -> {

				// ... and now that we have a message response from the server, we need to set
				// the text
				// of the label, however, that needs to be done on the UI thread, so we use
				// Platform.runLater(Runnable) to "send" our action to the correct thread :)
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// Go to the lobby
						try {
					    	System.out.println("Displaying lobby.");
					    	
					    	LobbyController lobbyController = getNodeNavigator().getController(Display.LOBBY);
					    	lobbyController.updateLobbyId();
					    	
							getNodeNavigator().display(Display.LOBBY);
							
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			});
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
