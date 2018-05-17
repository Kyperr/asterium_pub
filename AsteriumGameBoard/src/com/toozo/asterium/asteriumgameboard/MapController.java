package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.model.Location;
import com.toozo.asterium.util.GameResources;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.ClientConnectionHandler;

public class MapController {
	private ClientConnectionHandler ccHandler;

	@FXML
	private ListView<Location> locationListView;
	
	@FXML
	public void initialize() {
		locationListView.setCellFactory(lv -> new LocationListCell());
		
		//
	}
}
