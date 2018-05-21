package com.toozo.asterium.asteriumgameboard;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class MapController extends AbstractAsteriumController {

	@FXML
	ImageView boxImage;
	
	@FXML
	private ListView<String> locationListView;

	public void update() {

	}

	@Override
	protected void setup() {

	}
}
