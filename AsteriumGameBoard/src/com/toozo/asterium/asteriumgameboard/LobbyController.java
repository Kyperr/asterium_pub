package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

/**
 * Controller for lobby layout.
 * 
 * @author Jenna
 *
 */
public class LobbyController extends AbstractAsteriumController {

	private Node playerList;

	@FXML
	private Label label;

	@FXML
	private ScrollPane scrollPane;

	
	public void updateLobbyId() {
		try {
			label.setText(getGameResources().getLobbyId());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void setup() {
		try {//Somehow, I need to get playerlist to call initialize(a, b) before we get here.
			playerList = getNodeNavigator().getLayout(Display.PLAYER_LIST);
			scrollPane.setContent(playerList);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
