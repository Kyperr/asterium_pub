package com.toozo.asterium.controllers;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.nodes.LoopingBackGround;
import com.toozo.asterium.nodes.MapPane;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MapController extends AbstractAsteriumController {

	private Node locationList;

	@FXML
	private BorderPane mapInfoBorderPane;
	
	@FXML
	private Label foodLabel;
	
	@FXML
	private StackPane mapStackPane;
	
	private Image mapImage;
	
	private MapPane mapImagePane;	

	@Override
	protected void setup() {
		mapImage = new Image("/com/toozo/asterium/resources/map.png");
		try {
			mapImagePane = new MapPane(mapImage, getGameResources().getLocationMap());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapStackPane.getChildren().add(mapImagePane);
	}
	
	public MapController() {
		
	}
	
	public void update() {
		
		try {
			
			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
