package com.toozo.asterium.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.toozo.asterium.asteriumgameboard.GameBoardController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Utility class for controlling navigation between Nodes.
 *
 * All methods on the navigator are static to facilitate access from anywhere in
 * the application.
 */
public class NodeNavigator {

	public enum Display {
		MAIN("/com/toozo/asterium/fxml/gameboard.fxml"), 
		LOBBY("/com/toozo/asterium/fxml/lobby.fxml"), 
		MAP("/com/toozo/asterium/fxml/map.fxml"), 
		MENU("/com/toozo/asterium/fxml/menu.fxml"), 
		TURN_SUMMARY("/com/toozo/asterium/fxml/turnsummary.fxml"), 
		GAME_SUMMARY("/com/toozo/asterium/fxml/gamesummary.fxml");

		String fxmlLocation;

		Display(String fxmlLocation) {
			this.fxmlLocation = fxmlLocation;
		}

		public String getLocation() {
			return this.fxmlLocation;
		}

	}
	
	public NodeNavigator() {
		setChildControllers();
	}


	private Map<Display, Node> layoutMap = new HashMap<>();

	private Map<Display, FXMLLoader> loaderMap = new HashMap<>();


	private void setChildControllers() {

		try {

			for (Display view : Display.values()) {
				FXMLLoader loader = new FXMLLoader();
				Node node = loader.load(ClassLoader.class.getResourceAsStream(view.getLocation()));
				layoutMap.put(view, node);
				loaderMap.put(view, loader);
			}
			/*
			 * FXMLLoader loader = new FXMLLoader(); menuLayout =
			 * loader.load(ClassLoader.class.getResourceAsStream(MENU)); menuController =
			 * loader.getController();
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public <T> T getController(Display view) {
		return loaderMap.get(view).getController();
	}

	public void display(Display view) {
		mainController.setNode(layoutMap.get(view));
	}
	
	public Node getLayout(Display view) {
		return layoutMap.get(view);
	}

//	public static void loadLobby() {
//		lobbyController.updateLobbyId();
//		mainController.setNode(lobbyLayout);
//	}
}
