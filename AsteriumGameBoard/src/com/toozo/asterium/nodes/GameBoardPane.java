package com.toozo.asterium.nodes;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.controllers.AsteriumController;
import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameBoardPane extends StackPane implements AsteriumController{

	
	//Controller variables
	
	private boolean isInitialized = false;

	private GameResources gameResources;

	private NodeNavigator nodeNavigator;

	//Pane variables

	private LoopingBackGround backgroundImage;

	private StackPane nodeHolder;
	
	
	
	public GameBoardPane(Image backgroundImage) {
		this.nodeHolder = new StackPane();
		this.backgroundImage = new LoopingBackGround(backgroundImage, 50.0);
		init();
	}

	private void init() {
		//backgroundImage.fitHeightProperty().bind(this.heightProperty());
		
		getChildren().add(backgroundImage);
		getChildren().add(nodeHolder);
		
        
		
	}

	//Copied from Jenna's GameBoardController:

    public void setNode(Node node) {
    	this.nodeHolder.getChildren().setAll(node);
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
    //End copy
	
	
	
	@Override
	public void initialize(GameResources gameResources, NodeNavigator nodeNavigator) throws IllegalStateException {
		if (isInitialized) {
			throw new IllegalStateException("Initialization is being called on an already initialized AsteriumController.");
		} else {
			this.gameResources = gameResources;
			this.nodeNavigator = nodeNavigator;
			this.isInitialized = true;
		}
	}

}
