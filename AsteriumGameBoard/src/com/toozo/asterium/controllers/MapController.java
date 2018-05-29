package com.toozo.asterium.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.nodes.MapPane;

import actiondata.SyncGameBoardDataRequestData.PlayerCharacterData;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MapController extends AbstractAsteriumController {

	private Node locationList;

	@FXML
	private BorderPane mapInfoBorderPane;

	@FXML
	private Label foodLabel;

	@FXML
	private Label fuelLabel;
	
	@FXML
	private Label dayLabel;

	@FXML
	private VBox playersVBox;

	@FXML
	private StackPane mapStackPane;

	@FXML
	private Label playersTitle;
	
	@FXML
	private Label turnSummaryLabel;

	private Image mapImage;

	private MapPane mapImagePane;
	
	private Map<String, Label> playerLabelMap = new HashMap<String, Label>();

	@Override
	protected void setup() {
		try {
			mapImagePane = new MapPane(getGameResources());
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playersTitle.setText("Players:");
		mapStackPane.getChildren().add(mapImagePane);
	}

	public MapController() {

	}

	public void update() {
		try {

			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			fuelLabel.setText("Fuel: [" + getGameResources().getFuel() + "]");
			dayLabel.setText("Day: " + getGameResources().getDay());
			dayLabel.setText("Day: " + getGameResources().getDay()); 
			StringBuilder builder = new StringBuilder();
			builder.append("Turn Summary:\n");
			for (String s : getGameResources().getTurnSummary()) {
				builder.append(s);
				builder.append("\n");
			}
			turnSummaryLabel.setText(builder.toString());
			for (PlayerCharacterData player : getGameResources().getCharacters()) {
				if (!playerLabelMap.containsKey(player.getName())) {
					Label l = new Label(player.getName());
					playerLabelMap.put(player.getName(), l);
					playersVBox.getChildren().add(l);
				}
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endGame() {
		mapStackPane.getChildren().clear();
		turnSummaryLabel.setText("");
		Label gameSummary = new Label();
		StringBuilder builder = new StringBuilder();
		try {
			for (String s : getGameResources().getTurnSummary()) {
				builder.append(s);
				builder.append("\n");
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameSummary.setText(builder.toString()); 
		mapStackPane.getChildren().add(gameSummary);
	}

}
