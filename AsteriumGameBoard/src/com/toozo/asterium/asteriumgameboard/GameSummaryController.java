package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameSummaryController extends AbstractAsteriumController {

    @FXML
    private Label label;
    
    @FXML
    private Button lobbyIdButton;
    
    @FXML
    private Button backButton;
        
    public void handleMenuButtonAction(ActionEvent event) {
    	try {
			getNodeNavigator().display(Display.MENU);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
    }
    
    public void update() {
    	try {
			label.setText(getGameResources().getGameWonStatus());
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