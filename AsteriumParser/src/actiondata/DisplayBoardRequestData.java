package actiondata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.JoinAsPlayerRequestData.PlayerData;
import javafx.scene.paint.Color;

/**
 * {@link DisplayBoardRequestData} is the representation of data
 * to be used in a {@link Request} to display the game board.
 * 
 * Look on my works, ye mighty, and despair
 * 
 * @author Greg Schmitt
 *
 */
public class DisplayBoardRequestData extends AbstractRequestActionData {
	private Integer food;
	private Integer fuel;
	private Collection<DisplayBoardRequestData.LocationData> locations;
	private Collection<DisplayBoardRequestData.PlayerData> players;
	private Collection<DisplayBoardRequestData.VictoryData> victoryConditions;
	
	/**
	 * Assemble the data for a DisplayBoardRequest.
	 * 
	 * @param food The current amount of food.
	 * @param fuel The current amount of fuel.
	 * @param locations The locations to display.
	 * @param players The players to display.
	 * @param victoryConditions The victory conditions.
	 */
	public DisplayBoardRequestData(Integer food, Integer fuel,
								   Collection<DisplayBoardRequestData.LocationData> locations, 
								   Collection<DisplayBoardRequestData.PlayerData> players,
								   Collection<DisplayBoardRequestData.VictoryData> victoryConditions) {
		super(ActionData.DISPLAY_BOARD);
		this.food = food;
		this.fuel = fuel;
		this.locations = locations;
		this.players = players;
		this.victoryConditions = victoryConditions;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		
		// Add resources to data
		data.put(ActionData.FOOD, this.food);
		data.put(ActionData.FUEL, this.fuel);
		
		// Add players to data
		JSONArray players = new JSONArray();
		for (DisplayBoardRequestData.PlayerData player : this.players) {
			players.put(player.jsonify());
		}
		data.put(ActionData.PLAYERS, players);
		
		// Add locations to data
		JSONArray locations = new JSONArray();
		for (DisplayBoardRequestData.LocationData location : this.locations) {
			locations.put(location.jsonify());
		}
		data.put(ActionData.LOCATIONS, locations);
		
		// Add victory conditions to data
		JSONArray victoryConditions = new JSONArray();
		for (DisplayBoardRequestData.VictoryData victoryCondition : this.victoryConditions) {
			victoryConditions.put(victoryCondition.jsonify());
		}
		data.put(ActionData.VICTORY_CONDITIONS, victoryConditions);
		
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link DisplayBoardRequestData} object.
	 * 
	 * @param jsonObj	the {@link JSONObject} to be parsed
	 * @return	the {@link DisplayBoardRequestData} object parsed from JSON
	 * @throws JSONException
	 */
	public static DisplayBoardRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		// Parse resources
		Integer food = jsonObj.getInt(ActionData.FOOD);
		Integer fuel = jsonObj.getInt(ActionData.FUEL);
		
		// Parse array of locations
		JSONArray locationArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		Collection<DisplayBoardRequestData.LocationData> locations = new ArrayList<DisplayBoardRequestData.LocationData>();
		JSONObject locationObject;
		DisplayBoardRequestData.LocationData location;
		for (int i = 0; i < locationArray.length(); i++) {
			locationObject = locationArray.getJSONObject(i);
			location = new DisplayBoardRequestData.LocationData(locationObject.getInt(ActionData.MAP_LOCATION), 
																locationObject.getString(ActionData.TYPE));
			locations.add(location);
		}
		
		// Parse array of players
		JSONArray playerArray = jsonObj.getJSONArray(ActionData.PLAYERS);
		Collection<DisplayBoardRequestData.PlayerData> players = new ArrayList<DisplayBoardRequestData.PlayerData>();
		JSONObject playerObject;
		DisplayBoardRequestData.PlayerData player;
		for (int i = 0; i < playerArray.length(); i++) {
			playerObject = playerArray.getJSONObject(i);
			player = new DisplayBoardRequestData.PlayerData(playerObject.getString(ActionData.NAME), 
															Color.valueOf(playerObject.getString(ActionData.COLOR)), 
															playerObject.getInt(ActionData.MAP_LOCATION));
			players.add(player);
		}
		
		// Parse array of victory conditions
		// TODO
		JSONArray victoryArray = jsonObj.getJSONArray(ActionData.VICTORY_CONDITIONS);
		Collection<DisplayBoardRequestData.VictoryData> victories = new ArrayList<DisplayBoardRequestData.VictoryData>();
		JSONObject victoryObject;
		DisplayBoardRequestData.VictoryData victory;
		for (int i = 0; i < victoryArray.length(); i++) {
			victoryObject = victoryArray.getJSONObject(i);
			victory = new DisplayBoardRequestData.VictoryData(victoryObject.getString(ActionData.NAME), 
															  victoryObject.getInt(ActionData.CURRENT_VALUE), 
															  victoryObject.getInt(ActionData.MAX_VALUE));
			victories.add(victory);
		}
		
		// Construct and return
		return new DisplayBoardRequestData(food, fuel, locations, players, victories);
	}
	
	// ===== PLAYER DATA INNER CLASS =====
	/**
	 * {@link PlayerData} is the representation of a player
	 * for the purposes of displaying the board.
	 * 
	 * @author Greg Schmitt
	 *
	 */
	public static class PlayerData {
		private final String name;
		private final Color color;
		private final Integer mapLocation;

		public PlayerData(final String name, final Color color, final Integer location) {
			this.name = name;
			this.color = color;
			this.mapLocation = location;
		}
		
		public String getName() {
			return this.name;
		}
		
		public Color getColor() {
			return this.color;
		}
		
		public Integer getLocation() {
			return this.mapLocation;
		}

		/**
		 * @return	{@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.NAME, this.name);
			data.put(ActionData.COLOR, this.color.toString());
			data.put(ActionData.MAP_LOCATION, this.mapLocation);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof PlayerData) {
				PlayerData otherPlayerData = (PlayerData) other;
				return otherPlayerData.name.equals(this.name) &&
					   otherPlayerData.color.equals(this.color) &&
					   otherPlayerData.mapLocation.equals(this.mapLocation);
			} else {
				return false;
			}
		}
	}
	// ===================================
	
	// ===== LOCATION DATA INNER CLASS =====
	/**
	 * {@link LocationData} is the representation of a location
	 * for the purposes of displaying the board.
	 * 
	 * @author Greg Schmitt
	 *
	 */
	public static class LocationData {
		private final Integer mapLocation;
		private final String type;

		public LocationData(final Integer mapLocation, final String type) {
			this.mapLocation = mapLocation;
			this.type = type;
		}
		
		public Integer getMapLocation() {
			return this.mapLocation;
		}
		
		public String getType() {
			return this.type;
		}

		/**
		 * @return	{@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.MAP_LOCATION, this.mapLocation);
			data.put(ActionData.TYPE, this.type);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof DisplayBoardRequestData.LocationData) {
				DisplayBoardRequestData.LocationData otherLocationData = (DisplayBoardRequestData.LocationData) other;
				return otherLocationData.mapLocation.equals(this.mapLocation) &&
					   otherLocationData.type.equals(this.type);
			} else {
				return false;
			}
		}
	}
	// =====================================
	
	// ===== VICTORY DATA INNER CLASS =====
	/**
	 * {@link VictoryData} is the representation of a victory condition
	 * for the purposes of displaying the board.
	 * 
	 * @author Greg Schmitt
	 *
	 */
	public static class VictoryData {
		private final String conditionName;
		private final Integer currentValue;
		private final Integer maxValue;

		public VictoryData(final String conditionName, final Integer currentValue, final Integer maxValue) {
			this.conditionName = conditionName;
			this.currentValue = currentValue;
			this.maxValue = maxValue;
		}
		
		public String getName() {
			return this.conditionName;
		}
		
		public Integer getCurrentValue() {
			return this.currentValue;
		}
		
		public Integer getMaxValue() {
			return this.maxValue;
		}

		/**
		 * @return	{@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.NAME, this.conditionName);
			data.put(ActionData.CURRENT_VALUE, this.currentValue);
			data.put(ActionData.MAX_VALUE, this.maxValue);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof DisplayBoardRequestData.VictoryData) {
				DisplayBoardRequestData.VictoryData otherVictoryData = (DisplayBoardRequestData.VictoryData) other;
				return otherVictoryData.conditionName.equals(this.conditionName) &&
					   otherVictoryData.currentValue.equals(this.currentValue) &&
					   otherVictoryData.maxValue.equals(this.maxValue);
			} else {
				return false;
			}
		}
	}
	// =====================================
}
