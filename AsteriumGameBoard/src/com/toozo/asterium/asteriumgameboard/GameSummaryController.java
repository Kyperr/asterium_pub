package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameSummaryController extends AbstractAsteriumController {

    @FXML
    private Label label;
    
    @FXML
    private Button lobbyIdButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    public void initialize() {
    }    
    
    public void handleMenuButtonAction(ActionEvent event) {
    	try {
			getNodeNavigator().display(Display.MENU);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
    }
    
    public void update(GameResources gameResources) {
    	label.setText(gameResources.getGameWonStatus());
    }
    
}