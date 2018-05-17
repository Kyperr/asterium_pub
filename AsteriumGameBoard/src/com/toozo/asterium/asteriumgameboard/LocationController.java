package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.model.Location;
import com.toozo.asterium.model.Player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LocationController {
	private Location location;

	@FXML
	private Label nameLabel;

	@FXML
	private Label readyLabel;

	public void setLocation(Location location) {
		this.location = location;
		if (location == null) {
			nameLabel.setText(null);
		} else {
			nameLabel.setText(location.getLocationName());;
		}
	}
}
