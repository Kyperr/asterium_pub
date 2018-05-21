package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator.Display;

import actiondata.SyncGameBoardDataRequestData.LocationData;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MapController extends AbstractAsteriumController {

	private Node locationList;

	@FXML
	private Label locationLabel;
	
	@FXML
	private Label foodLabel;

	@FXML
	private ScrollPane scrollPane;

	@Override
	protected void setup() {
		locationLabel.setText("Available locations:");
	}
	
	public void update() {
		try {
			
			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			
			GridPane gridPane = new GridPane();
			int i = 0;
			for (LocationData loc : getGameResources().getLocations()) {
				Label locLabel = new Label(loc.getMapLocation() + "(" + loc.getType() + ")");
				gridPane.add(locLabel, 0, i);
				i++;
			}

			scrollPane.setContent(gridPane);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
