package com.toozo.asterium.util;

import java.io.IOException;

import com.toozo.asterium.asteriumgameboard.GameBoardController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Utility class for controlling navigation between Nodes.
 *
 * All methods on the navigator are static to facilitate access from anywhere in the application.
 */
public class NodeNavigator {

    public static final String MAIN = "/com/toozo/asterium/fxml/gameboard.fxml";
    private static final String LOBBY = "/com/toozo/asterium/fxml/lobby.fxml";
    private static final String MAP = "/com/toozo/asterium/fxml/map.fxml";
    private static final String MENU = "/com/toozo/asterium/fxml/menu.fxml";
    private static final String TURN_SUMMARY = "/com/toozo/asterium/fxml/turnsummary.fxml";
    private static final String GAME_SUMMARY = "/com/toozo/asterium/fxml/gamesummary.fxml";

    /* The main application layout controller. */
    private static GameBoardController mainController;
    
    /* Layout controllers */
    private static Node menuController;
    private static Node lobbyController;
    private static Node mapController;
    private static Node turnSummaryController;
    private static Node gameSummaryController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(GameBoardController mainController) {
        NodeNavigator.mainController = mainController;
    }
    
    public void setChildControllers() {
    	
		try {
			menuController = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(MENU));
			lobbyController = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(LOBBY));
			mapController = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(NodeNavigator.MAP));
			turnSummaryController = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(TURN_SUMMARY));
			gameSummaryController = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(GAME_SUMMARY));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

   /**
    * 
    * @param fxml
    */
    public static void loadNode(String fxml) {
        try {
            mainController.setNode((FXMLLoader.load(NodeNavigator.class.getResource(fxml))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadMenu() {
       mainController.setNode(menuController);
    }
    
    public static void loadLobby() {
        mainController.setNode(lobbyController);
     }
    
    public static void loadMap() {
        mainController.setNode(mapController);
     }
    
    public static void loadTurnSummary() {
        mainController.setNode(turnSummaryController);
     }
    
    public static void loadGameSummary() {
        mainController.setNode(turnSummaryController);
     }

    public static GameBoardController getMainController() {
    	return mainController;
    }
}
