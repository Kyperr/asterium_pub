package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * Controller for lobby layout.
 * @author Jenna
 *
 */
public class LobbyController extends AbstractAsteriumController{
	
	private Pane playerList;
	
	@FXML
	private Label label;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	public void initialize() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toozo/asterium/fxml/playerlist.fxml"));
        try {
			playerList = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scrollPane.setContent(playerList);
	}
	
	public void updateLobbyId() {
		try {
			label.setText(getGameResources().getLobbyId());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updatePlayerViews() {
		
	}
}
