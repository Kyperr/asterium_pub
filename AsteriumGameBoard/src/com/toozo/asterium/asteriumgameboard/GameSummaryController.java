package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameSummaryController {

    @FXML
    private Label label;
    private Button lobbyIdButton;
    private Button backButton;
    
    @FXML
    public void initialize() {
    }    
    
    public void handleMenuButtonAction(ActionEvent event) {
    	NodeNavigator.loadMenu();
    }
    
    public void update() {
    	label.setText(GameResources.getGameWonStatus());
    }
    
}