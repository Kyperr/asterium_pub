package com.toozo.asterium.asteriumgameboard;

import actiondata.SyncGameBoardDataRequestData.LocationData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LocationController {
	private String location;

	@FXML
	private Label nameLabel;

	@FXML
	private Label readyLabel;

	public void setLocation(String location) {
		this.location = location;
		if (location == null) {
			nameLabel.setText(null);
		} else {
			nameLabel.setText(location);
		}
	}
}
