package com.toozo.asterium.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toozo.asterium.controllers.GameSummaryController;
import com.toozo.asterium.controllers.MapController;
import com.toozo.asterium.controllers.PlayerListController;
import com.toozo.asterium.controllers.TurnSummaryController;
import com.toozo.asterium.util.NodeNavigator.Display;

import actiondata.ActionData;
import actiondata.SyncData.ItemData;
import actiondata.SyncData.LocationData;
import actiondata.SyncGameBoardDataRequestData;
import actiondata.SyncGameBoardDataRequestData.PlayerCharacterData;
import actiondata.SyncGameBoardDataRequestData.VictoryData;
import actiondata.SyncPlayerListRequestData;
import actiondata.SyncPlayerListRequestData.PlayerData;
import actiondata.TurnSummaryRequestData;
import javafx.application.Platform;
import main.ClientConnectionHandler;

/**
 * Store game data needed by the gameboard and any needed functionality.
 * 
 * All methods on the navigator are static to facilitate access from anywhere in the application.
 * @author Jenna
 */
public final class GameResources {
	
	private final String URI = "ws://localhost:8080/AsteriumWebServer/Game";	
	
	private final String END_SUMMARY = "END_SUMMARY";
	
	private final String TURN_SUMMARY = "TURN_SUMMARY";
	
	private String lobbyId = "no lobby id";
	
	private String authToken = "";

	private ClientConnectionHandler ccHandler;
	
	private NodeNavigator nodeNavigator;
	
	/* =============== Game Data ============== */
	
	private Integer food = 0;
	
	private Integer fuel = 0;
	
	private Integer day = 1;
	
	private Map<Integer, String> locationMap = new HashMap<Integer, String>();
	
	private List<PlayerData> players = new ArrayList<PlayerData>();
	
	private List<PlayerCharacterData> characters = new ArrayList<PlayerCharacterData>();
	
	private List<LocationData> locations = new ArrayList<LocationData>();
	
	private List<VictoryData> victoryConditions = new ArrayList<VictoryData>();
	
	private List<ItemData> communalInventory = new ArrayList<ItemData>();
	
	private boolean gameWonStatus = false;
	
	private String gamePhase = "";
	
	private List<String> turnSummary = new ArrayList<String>();
	
	public GameResources(NodeNavigator nodeNavigator) {
		this.nodeNavigator = nodeNavigator;
		ccHandler = new ClientConnectionHandler(URI);
		registerCallbacks();
	}
	
	public void setLobbyId(String id) {
		lobbyId = id;
	}

	public String getLobbyId() {
		return lobbyId;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	
	public ClientConnectionHandler getClientConnectionHandler() {
    	return ccHandler;
    }
	
	public Integer getFood() {
		return food;
	}
	
	public Integer getFuel() {
		return fuel;
	}
	
	public List<PlayerData> getPlayers() {
		return players;
	}
	
	public List<PlayerCharacterData> getCharacters() {
		return characters;
	}
	
	public List<ItemData> getCommunalInventory() {
		return communalInventory;
	}
	
	public List<LocationData> getLocations() {
		return locations;
	}

	public List<VictoryData> getVictoryConditions() {
		return victoryConditions;
	}

	public Map<Integer, String> getLocationMap() {
		return locationMap;
	}
	
	public void setGameWonStatus(boolean status) {
		gameWonStatus = status;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public List<String> getTurnSummary() {
		return turnSummary;
	}
	
	public String getGameWonStatus() {
		StringBuilder endOfGameMessage = new StringBuilder();
		if (gameWonStatus) {
			endOfGameMessage.append("You won!");
		} else {
			endOfGameMessage.append("You lost.");
		}
		return endOfGameMessage.toString();
	}
	
	public void registerCallbacks() {
		
		// Register a response with the server: what should happen 
		// when we receive a list of locations.
		ccHandler.registerRequestCallback(ActionData.SYNC_GAME_BOARD_DATA, (message) -> {
			 System.err.println("Received sync_game_board_data");
			Platform.runLater(new Runnable() {
				 @Override public void run() {
					 // Get the data
					 SyncGameBoardDataRequestData data = SyncGameBoardDataRequestData.class.cast(message.getActionData());
					 // Sync the data
					 food = data.getFood();
					 fuel = data.getFuel();
					 day = data.getDay();
					 victoryConditions = (List<VictoryData>) data.getVictoryConditions();
					 locations = (List<LocationData>) data.getLocations();
					 putMapLocations();
					 communalInventory = (List<ItemData>) data.getCommunalInventory();
					 characters = (List<PlayerCharacterData>) data.getPlayers();
					 gamePhase = data.getGamePhase();
					 if (gamePhase.equals(END_SUMMARY)) {
						 System.out.println("END OF GAME");
						 
					 } else if (data.getGamePhase() == TURN_SUMMARY) {
						 gamePhase = TURN_SUMMARY; 
						 TurnSummaryController controller = nodeNavigator.getController(Display.TURN_SUMMARY);
						 controller.update();
						 nodeNavigator.display(Display.TURN_SUMMARY);
					 } else {
						 MapController controller = nodeNavigator.getController(Display.MAP);
						 controller.update();
						 nodeNavigator.display(Display.MAP);
					 }					 
				 }
				
			});
		});
		
		ccHandler.registerRequestCallback(ActionData.SUMMARY, (message) -> {
			 System.err.println("Received turn_summary");
			Platform.runLater(new Runnable() {
				 @Override public void run() {
					 // Get the data
					 TurnSummaryRequestData data = TurnSummaryRequestData.class.cast(message.getActionData());
					 // Sync the data
					 turnSummary = data.getSummary();
					 
					 if (gamePhase == END_SUMMARY) {
						 MapController controller = nodeNavigator.getController(Display.MAP);
						 controller.endGame();
					 } else {
						 MapController controller = nodeNavigator.getController(Display.MAP);
						 controller.update();
						 nodeNavigator.display(Display.MAP);
					 }					 
				 }
				
			});
		});
		
		// Register a response with the server: what should happen 
		// when we receive a list of players
		ccHandler.registerRequestCallback(ActionData.SYNC_PLAYER_LIST,  (message) -> {
			 System.err.println("Received sync_player_list");
			Platform.runLater(new Runnable() {
				 @Override public void run() {
					 // Get the data
					 SyncPlayerListRequestData data = SyncPlayerListRequestData.class.cast(message.getActionData());
					 // Sync the data
					 players = (List<PlayerData>) data.getPlayers();
					 
					 PlayerListController controller = nodeNavigator.getController(Display.PLAYER_LIST);
					 
					 controller.updatePlayers(players);
				 }
				
			});
		});
		
	}
	
	/**
	 * Map locations to positions based on sync data
	 */
	private void putMapLocations() {
		// Map each location
		for (LocationData loc : locations) {
			locationMap.put(loc.getPosition(), loc.getName());
		}
		
	}
}
