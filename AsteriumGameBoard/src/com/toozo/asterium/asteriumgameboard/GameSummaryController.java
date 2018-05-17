package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.ClientConnectionHandler;
import message.Request;

public class GameSummaryController {

	private static final String fxml = "com/toozo/asterium/fxml/lobby.fxml";
	
	private ClientConnectionHandler ccHandler;
	
    @FXML
    private Label label;
    private Button lobbyIdButton;
    private Button backButton;
    
    public GameSummaryController() {
    	ccHandler = GameResources.getClientConnectionHandler();
    }
    
    
    @FXML
    public void initialize() {
    	label.setText(GameResources.getGameWonStatus());
    }    
    
}