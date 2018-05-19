package com.toozo.asterium.util;

import java.io.IOException;

import com.toozo.asterium.asteriumgameboard.GameBoardController;
import com.toozo.asterium.asteriumgameboard.GameSummaryController;
import com.toozo.asterium.asteriumgameboard.LobbyController;
import com.toozo.asterium.asteriumgameboard.MapController;
import com.toozo.asterium.asteriumgameboard.MenuController;
import com.toozo.asterium.asteriumgameboard.TurnSummaryController;

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
    
    /* Layout nodes */
    private static Node menuLayout;
    private static Node lobbyLayout;
    private static Node mapLayout;
    private static Node turnSummaryLayout;
    private static Node gameSummaryLayout;
    
    /* Layout controllers */
    private static MenuController menuController;
    private static LobbyController lobbyController;
    private static MapController mapController;
    private static TurnSummaryController turnSummaryController;
    private static GameSummaryController gameSummaryController;
    
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
			FXMLLoader loader = new FXMLLoader();
			menuLayout = loader.load(ClassLoader.class.getResourceAsStream(MENU));
			menuController = loader.getController();
			loader = new FXMLLoader();
			lobbyLayout = loader.load(ClassLoader.class.getResourceAsStream(LOBBY));
			lobbyController = loader.getController();
			loader = new FXMLLoader();
			mapLayout = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(NodeNavigator.MAP));
			mapController = loader.getController();
			loader = new FXMLLoader();
			turnSummaryLayout = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(TURN_SUMMARY));
			turnSummaryController = loader.getController();
			loader = new FXMLLoader();
			gameSummaryLayout = new FXMLLoader().load(ClassLoader.class.getResourceAsStream(GAME_SUMMARY));
			gameSummaryController = loader.getController();
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
       mainController.setNode(menuLayout);
    }
    
    public static void loadLobby() {
    	lobbyController.updateLobbyId();
        mainController.setNode(lobbyLayout);
     }
    
    public static void loadMap() {
        mainController.setNode(mapLayout);
     }
    
    public static void loadTurnSummary() {
        mainController.setNode(turnSummaryLayout);
     }
    
    public static void loadGameSummary() {
        mainController.setNode(gameSummaryLayout);
     }

    public static GameBoardController getMainController() {
    	return mainController;
    }
}
