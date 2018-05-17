package com.toozo.asterium.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.toozo.asterium.asteriumgameboard.GameBoardController;

/**
 * Utility class for controlling navigation between Nodes.
 *
 * All methods on the navigator are static to facilitate access from anywhere in the application.
 */
public class NodeNavigator {

    /*
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN = "com/toozo/asterium/fxml/gameboard.fxml";
    public static final String NODE_1 = "com/toozo/asterium/fxml/menu.fxml";
    public static final String NODE_2 = "com/toozo/asterium/fxml/lobby.fxml";

    /* The main application layout controller. */
    private static GameBoardController mainController;
    

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(GameBoardController mainController) {
        NodeNavigator.mainController = mainController;
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

    public static GameBoardController getMainController() {
    	return mainController;
    }
}
