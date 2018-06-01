package com.toozo.asterium.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.nodes.MapPane;

import actiondata.SyncGameBoardDataRequestData.PlayerCharacterData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MapController extends AbstractAsteriumController {

	/*
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
	private VBox summaryVBox;
	
	@FXML
	private StackPane mapStackPane;

	@FXML
	private Label playersTitle;
	
	@FXML
	private Label turnSummaryLabel;
	*/
	
	@FXML
	GridPane mapPane;
	
	private Label playersHeader = new Label("Players:");
	
	private Label fuelLabel = new Label();
	
	private Label foodLabel = new Label();
	
	private Label dayLabel = new Label();
	
	private VBox playersVBox = new VBox();
	
	private MapPane mapImagePane;
	
	private ScrollPane turnSummaryScrollPane = new ScrollPane();
	
	private Label turnSummaryLabel = new Label();
	
	private Map<String, Label> playerLabelMap = new HashMap<String, Label>();

	@Override
	protected void setup() {
		try {
			mapImagePane = new MapPane(getGameResources());
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        foodLabel.setTextFill(Color.WHITE);
        fuelLabel.setTextFill(Color.WHITE);
        dayLabel.setTextFill(Color.WHITE);
        playersHeader.setTextFill(Color.WHITE);
        
        turnSummaryScrollPane.setStyle("-fx-background-color: transparent;");
		
		RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(60);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(12);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(33);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(15);
        mapPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        mapPane.getRowConstraints().addAll(row1,row2,row3);
        
        GridPane.setConstraints(foodLabel, 1, 0, 1, 1);
        GridPane.setConstraints(fuelLabel, 2, 0, 1, 1);
        GridPane.setConstraints(dayLabel, 4, 0, 1, 1);
        GridPane.setConstraints(playersVBox, 4, 1, 1, 1);
        GridPane.setConstraints(mapImagePane, 1, 1, 3, 1);
        GridPane.setConstraints(turnSummaryScrollPane, 1, 2, 4, 1);
        
        playersVBox.getChildren().add(playersHeader);
        mapPane.getChildren().addAll(foodLabel, fuelLabel, dayLabel,
        		playersVBox, mapImagePane, turnSummaryScrollPane);
        
        turnSummaryScrollPane.setContent(turnSummaryLabel);
		//mapStackPane.getChildren().add(mapImagePane);
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
			turnSummaryLabel.setTextFill(Color.WHITE);
			for (PlayerCharacterData player : getGameResources().getCharacters()) {
				if (!playerLabelMap.containsKey(player.getName())) {
					Label l = new Label(player.getName());
					l.setTextFill(Color.WHITE);
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
		
		try {
			foodLabel.setText("Food storage: [" + getGameResources().getFood() + "]");
			fuelLabel.setText("Fuel: [" + getGameResources().getFuel() + "]");
			dayLabel.setText("Day: " + getGameResources().getDay());
			dayLabel.setText("Day: " + getGameResources().getDay()); 
			StringBuilder builder = new StringBuilder();
			builder.append("Game Summary:\n");
			for (String s : getGameResources().getTurnSummary()) {
				builder.append(s);
				builder.append("\n");
			}
			turnSummaryLabel.setText(builder.toString());
			turnSummaryLabel.setTextFill(Color.WHITE);
			for (PlayerCharacterData player : getGameResources().getCharacters()) {
				if (!playerLabelMap.containsKey(player.getName())) {
					Label l = new Label(player.getName());
					l.setTextFill(Color.WHITE);
					playerLabelMap.put(player.getName(), l);
					playersVBox.getChildren().add(l);
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
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
		*/
	}
	

}
