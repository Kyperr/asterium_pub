package com.toozo.asterium.controllers;

import java.net.URISyntaxException;

import javax.resource.spi.IllegalStateException;

import actiondata.SyncData.LocationData;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapController extends AbstractAsteriumController {

	private Node locationList;
	
	private Image image;
	private ImageView imageView;

	@FXML
	private BorderPane mapInfoBorderPane;
	
	@FXML
	private Label foodLabel;
	
	@FXML
	private StackPane mapStackPane;

	@Override
	protected void setup() {
		try {
			image = new Image(
					getClass().getResource("/com/toozo/asterium/resources/map.jpg").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageView = new ImageView(image);
		mapStackPane.getChildren().add(imageView);
		
	}
	
	public MapController() {
		
	}
	
	public void update() {
		
		try {
			
			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			
			/*
			int i = 0;
			for (LocationData loc : getGameResources().getLocations()) {
				Label locLabel = new Label(loc.getName() + "(" + loc.getType().toString() + ")");
				gridPane.add(locLabel, 0, i);
				gridPane.setAlignment(Pos.CENTER);
				gridPane.setPadding(new Insets(5,2,5,2));
				i++;
			}
			*/
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drawLocations();
	}
	
	private void drawLocations() {
		
	}
}
