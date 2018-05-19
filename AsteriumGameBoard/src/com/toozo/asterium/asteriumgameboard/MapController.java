package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.util.NodeNavigator;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MapController {

	@FXML
	private ListView<String> locationListView;
	
	
	@FXML
	public void initialize() {
		//locationListView.setCellFactory(lv -> new LocationListCell());
		
	}
	
	@FXML
	public void handleReadyButtonAction() {
		NodeNavigator.loadTurnSummary();
	}
	
	public void update() {
		
	}
}
