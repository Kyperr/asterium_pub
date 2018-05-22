package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import actiondata.SyncGameBoardDataRequestData.LocationData;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MapController extends AbstractAsteriumController {

	private Node locationList;

	@FXML
	private BorderPane mapInfoBorderPane;
	
	@FXML
	private Label foodLabel;

	@FXML
	private ScrollPane mapScrollPane;

	@Override
	protected void setup() {
	}
	
	public void update() {
		try {
			
			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			
			GridPane gridPane = new GridPane();
			gridPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
			int i = 0;
			for (LocationData loc : getGameResources().getLocations()) {
				Label locLabel = new Label(loc.getName() + "(" + loc.getType().toString() + ")");
				gridPane.add(locLabel, 0, i);
				i++;
			}

			mapScrollPane.setContent(gridPane);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
