package com.toozo.asterium.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.asteriumgameboard.AbstractAsteriumController;
import com.toozo.asterium.asteriumgameboard.GameBoardController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Utility class for controlling navigation between Nodes.
 *
 * All methods on the navigator are static to facilitate access from anywhere in
 * the application.
 */
public class NodeNavigator {

	public static enum Display {
		LOBBY("/com/toozo/asterium/fxml/lobby.fxml"),
		MAP("/com/toozo/asterium/fxml/map.fxml"),
		MENU("/com/toozo/asterium/fxml/menu.fxml"),
		TURN_SUMMARY("/com/toozo/asterium/fxml/turnsummary.fxml"),
		GAME_SUMMARY("/com/toozo/asterium/fxml/gamesummary.fxml"),
		PLAYER_LIST("/com/toozo/asterium/fxml/playerlist.fxml");

		String fxmlLocation;

		Display(String fxmlLocation) {
			this.fxmlLocation = fxmlLocation;
		}

		public String getLocation() {
			return this.fxmlLocation;
		}

	}

	private Map<Display, Node> layoutMap = new HashMap<>();

	private Map<Display, AbstractAsteriumController> loaderMap = new HashMap<>();

	private GameResources gameResources;
	
	private GameBoardController gameBoardController;

	public NodeNavigator(GameBoardController gameBoardController) {
		gameResources = new GameResources(this);
		try {
			gameBoardController.initialize(this.gameResources, this);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gameBoardController = gameBoardController;
		setChildControllers();
	}

	private void setChildControllers() {

		try {

			for (Display view : Display.values()) {
				FXMLLoader loader = new FXMLLoader();

				Node node = loader.load(ClassLoader.class.getResourceAsStream(view.getLocation()));
				
				layoutMap.put(view, node);

				AbstractAsteriumController controller = loader.getController();

				loaderMap.put(view, controller);
			}
			
			//Once they all exist, initialize all of them.
			for(AbstractAsteriumController aac: loaderMap.values()) {
				try {
					aac.initialize(this.gameResources, this);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

	@SuppressWarnings("unchecked") // This is what javafx does. Not my fault.
	public <T extends AbstractAsteriumController> T getController(Display view) {
		return (T) loaderMap.get(view);
	}

	public void display(Display view) {
		gameBoardController.setNode(layoutMap.get(view));
	}

	public Node getLayout(Display view) {
		return layoutMap.get(view);
	}

	public GameBoardController getMainController() {
		return this.gameBoardController;
	}
}
