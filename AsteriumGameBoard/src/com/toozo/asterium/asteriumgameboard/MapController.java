package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.NodeNavigator;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MapController extends AbstractAsteriumController {

	@FXML
	private ListView<String> locationListView;
		
	public void update() {
		
	}

	@Override
	protected void setup() {
	}
}
