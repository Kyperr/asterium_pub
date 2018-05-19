package com.toozo.asterium.asteriumgameboard;

import java.net.URL;
import java.util.ResourceBundle;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.ClientConnectionHandler;
import message.Request;

/**
 * Controller for main menu layout.
 * @author Jenna
 *
 */
public class MenuController {	
	
    @FXML
    private Label label;
    private Button lobbyIdButton;
    private Button backButton;
    
    @FXML
    private void handleLobbyIdButtonAction(ActionEvent event) {
    	NodeNavigator.loadLobby();
    	//CreateGameRequestData cgrData = new CreateGameRequestData();
    	    	
        //Request request = new Request(cgrData, "");
        
        //Here, we say that once we get the message back, run the lambda...
    	/*GameResources.getClientConnectionHandler().send(request.jsonify().toString(), (message) -> {
    		
    		//... and now that we have a message response from the server, we need to set the text
    		//of the label, however, that needs to be done on the UI thread, so we use 
    		//Platform.runLater(Runnable) to "send" our action to the correct thread :)
    		Platform.runLater(new Runnable() {
                @Override public void run() {
                	
                	//This is our response data, formed from the message.
            		CreateGameResponseData responseData = CreateGameResponseData.fromMessage(message);
            		        	
            		// Save the lobby id
                	GameResources.setLobbyId(responseData.getLobbyID());
                	
                	// Go to the lobby
                	NodeNavigator.loadLobby();
                }
            });
    		
    	});*/
    }
    
    @FXML
    public void initialize() {
    }    
    
}
