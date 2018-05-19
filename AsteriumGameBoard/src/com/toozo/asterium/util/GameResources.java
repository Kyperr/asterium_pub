package com.toozo.asterium.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import actiondata.ActionData;
import actiondata.SyncGameBoardDataRequestData;
import actiondata.SyncGameBoardDataRequestData.ItemData;
import actiondata.SyncGameBoardDataRequestData.LocationData;
import actiondata.SyncGameBoardDataRequestData.PlayerCharacterData;

import actiondata.SyncGameBoardDataRequestData.VictoryData;
import actiondata.SyncPlayerListRequestData;
import actiondata.SyncPlayerListRequestData.PlayerData;
import javafx.application.Platform;
import main.ClientConnectionHandler;

/**
 * Store game data needed by the gameboard and any needed functionality.
 * 
 * All methods on the navigator are static to facilitate access from anywhere in the application.
 * @author Jenna
 */
public class GameResources {
	
	private static final String URI = "ws://localhost:8080/AsteriumWebServer/Game";	
	
	private static String lobbyId = "no lobby id";

	private static ClientConnectionHandler ccHandler;
	
	/* =============== Game Data ============== */
	
	private static Integer food = 0;
	
	private static Integer fuel = 0;
	
	private static List<PlayerData> players = new ArrayList<PlayerData>();
	
	private static List<PlayerCharacterData> characters = new ArrayList<PlayerCharacterData>();
	
	private static List<LocationData> locations = new ArrayList<LocationData>();
	
	private static List<VictoryData> victoryConditions = new ArrayList<VictoryData>();
	
	private static  List<ItemData> communalInventory = new ArrayList<ItemData>();
	
	private static boolean gameWonStatus = false;
	
	public GameResources() {
		ccHandler = new ClientConnectionHandler(URI);
//		registerCallbacks();
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
	
	public Integer getFood() {
		return food;
	}
	
	public Integer getFuel() {
		return fuel;
	}
	
	public static List<PlayerData> getPlayers() {
		return players;
	}
	
	public static List<PlayerCharacterData> getCharacters() {
		return characters;
	}
	
	public static List<ItemData> getCommunalInventory() {
		return communalInventory;
	}
	
	public static List<LocationData> getLocations() {
		return locations;
	}

	public static List<VictoryData> getVictoryConditions() {
		return victoryConditions;
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
	
	public static void registerCallbacks() {
		
		// Register a response with the server: what should happen 
		// when we receive a list of locations.
		ccHandler.registerRequestCallback(ActionData.SYNC_GAME_BOARD_DATA, (message) -> {
			Platform.runLater(new Runnable() {
				 @Override public void run() {
					 // Get the data
					 SyncGameBoardDataRequestData data = SyncGameBoardDataRequestData.class.cast(message.getActionData());
					 // Sync the data
					 food = data.getFood();
					 fuel = data.getFuel();
					 victoryConditions = (List<VictoryData>) data.getVictoryConditions();
					 locations = (List<LocationData>) data.getLocations();
					 communalInventory = (List<ItemData>) data.getCommunalInventory();
					 characters = (List<PlayerCharacterData>) data.getPlayers();
				 }
				
			});
		});
		
		// Register a response with the server: what should happen 
		// when we receive a list of players
		ccHandler.registerRequestCallback(ActionData.SYNC_PLAYER_LIST,  (message) -> {
			Platform.runLater(new Runnable() {
				 @Override public void run() {
					 // Get the data
					 SyncPlayerListRequestData data = SyncPlayerListRequestData.class.cast(message.getActionData());
					 // Sync the data
					 players = (List<PlayerData>) data.getPlayers();
				 }
				
			});
		});
		
	}
	
}
