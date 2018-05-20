/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;

import com.toozo.asterium.util.NodeNavigator;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
 * !!!!!IMPORTANT!!!!!
 * Make sure you set up your eclipse to handle JavaFX, here is a link:
 * https://www.eclipse.org/efxclipse/install.html#for-the-ambitious
 */

/**
 *
 *
 *
 * @author Jenna, Daniel
 */
public class AsteriumGameBoard extends Application {

	private static final String GAME_BOARD_TITLE = "Asterium";
	private static final String CONTAINER_CSS = "css/asteriumgameboard.css";
	private static final int PANE_WIDTH = 275;
	private static final int PANE_HEIGHT = 200;

	@Override
	public void start(Stage stage) throws Exception {

		NodeNavigator navigator = new NodeNavigator();

		stage.setTitle(GAME_BOARD_TITLE);
		stage.setScene(createScene(navigator.getMainController()));
		stage.show();
		navigator.display(Display.MENU);
	}

	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		// Pane mainPane = (Pane)
		// loader.load(ClassLoader.class.getResourceAsStream(NodeNavigator.Display.MAIN.getLocation()));

		GameBoardController mainController = loader.getController();

		NodeNavigator navigator = new NodeNavigator();
		navigator.setChildControllers();

		navigator.setMainController(mainController);
		navigator.display(Display.MENU);

		return mainPane;
	}

	/**
	 * Creates the main application scene.
	 *
	 * @param mainPane
	 *            the main application layout.
	 *
	 * @return the created scene.
	 */
	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane, PANE_WIDTH, PANE_HEIGHT);

		return scene;
	}

	/**
	 * Creates a game.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
