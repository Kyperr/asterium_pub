package com.toozo.asterium.util;

import java.util.ArrayList;
import java.util.List;

import com.toozo.asterium.model.Player;

import main.ClientConnectionHandler;

/**
 * Store game data needed by the gameboard and any needed functionality.
 * 
 * All methods on the navigator are static to facilitate access from anywhere in the application.
 * @author Jenna
 */
public class GameResources {
	
	private static final String URI = "ws://localhost:8080/AsteriumWebServer/Game/";	
	
	private static String lobbyId = "";

	private static ClientConnectionHandler ccHandler;
	
	private static final List<Player> playerList = new ArrayList<Player>();
	
	private static boolean gameWonStatus;
	
	public GameResources() {
		ccHandler = new ClientConnectionHandler(URI);
	}
	
	public static void setLobbyId(String id) {
		lobbyId = id;
	}
	
	public static String getLobbyId() {
		return lobbyId;
	}
	
	public static ClientConnectionHandler getClientConnectionHandler() {
    	return ccHandler;
    }
	
	public static List<Player> getPlayers() {
		return playerList;
	}

	public static void setGameWonStatus(boolean status) {
		gameWonStatus = status;
	}
	
	public static String getGameWonStatus() {
		StringBuilder endOfGameMessage = new StringBuilder();
		if (gameWonStatus) {
			endOfGameMessage.append("You won!");
		} else {
			endOfGameMessage.append("You lost.");
		}
		return endOfGameMessage.toString();
	}
}
