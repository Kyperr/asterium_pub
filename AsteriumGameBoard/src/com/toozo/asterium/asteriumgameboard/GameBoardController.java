package com.toozo.asterium.asteriumgameboard;

import java.net.URL;
import java.util.ResourceBundle;

import com.toozo.asterium.util.GameResources;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import main.ClientConnectionHandler;

/**
 * Main controller class for the entire layout.
 */
public class GameBoardController {
	
    /** Holder of a node. Initialized by fxml. */
    @FXML
    private StackPane nodeHolder;
    private static GameResources resources;
 
    public GameBoardController() {
    	resources = new GameResources();
    }
    
    public void setNode(Node node) {
    	nodeHolder.getChildren().setAll(node);
    }
    
    public void addNewNode(Node node) {
        nodeHolder.getChildren().add(node);
    }
    
    public void switchNode(Node node) {
    	if(nodeHolder.getChildren().contains(node)) {
    		nodeHolder.getChildren().remove(node);
    		nodeHolder.getChildren().add(0, node);
    	}
    }
    
    public ObservableList<Node> getNodes() {
    	return nodeHolder.getChildren();
    }
    
    @FXML
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
    
}
