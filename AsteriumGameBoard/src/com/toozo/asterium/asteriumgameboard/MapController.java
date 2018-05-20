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
	
	
	@FXML
	public void initialize() {
		//locationListView.setCellFactory(lv -> new LocationListCell());
		
	}
	
	@FXML
	public void handleReadyButtonAction() {
		try {
			getNodeNavigator().display(Display.TURN_SUMMARY);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
}
