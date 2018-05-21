package com.toozo.asterium.asteriumgameboard;

import java.net.URL;
import java.util.ResourceBundle;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import main.ClientConnectionHandler;

/**
 * Main controller class for the entire layout.
 */
public class GameBoardController extends AbstractAsteriumController {
	
    /** Holder of a node. Initialized by fxml. */
    @FXML
    private StackPane nodeHolder;
    private GameResources resources;
 
    public GameBoardController() {
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
    
    public void removehNode(Node node) {
    	if(nodeHolder.getChildren().contains(node)) {
    		nodeHolder.getChildren().remove(node);
    	}
    }
    
    public ObservableList<Node> getNodes() {
    	return nodeHolder.getChildren();
    }    
}
