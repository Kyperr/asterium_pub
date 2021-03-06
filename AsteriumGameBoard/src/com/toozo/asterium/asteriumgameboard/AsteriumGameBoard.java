/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;

import com.toozo.asterium.controllers.GameBoardController;
import com.toozo.asterium.nodes.GameBoardPane;
import com.toozo.asterium.util.NodeNavigator;
import com.toozo.asterium.util.NodeNavigator.Display;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

	private static String MAIN = "/com/toozo/asterium/fxml/gameboard.fxml";

	private static final String GAME_BOARD_TITLE = "Asterium";
	private static final String CONTAINER_CSS = "css/asteriumgameboard.css";
	private static final int PANE_WIDTH = 960;
	private static final int PANE_HEIGHT = 540;

	@Override
	public void start(Stage stage) throws Exception {

		Image backgroundImage = new Image(
				getClass().getResource("/com/toozo/asterium/resources/background.jpg").toURI().toString());
		GameBoardPane gameboardPane = new GameBoardPane(backgroundImage);

		NodeNavigator navigator = new NodeNavigator(gameboardPane);
		
		stage.setTitle(GAME_BOARD_TITLE);
		stage.setScene(createScene(gameboardPane));
		stage.setMaximized(true);
		stage.show();
		navigator.display(Display.MENU);

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
		scene.getStylesheets()
				.addAll(this.getClass().getResource("/com/toozo/asterium/css/asteriumgameboard.css").toExternalForm());
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
