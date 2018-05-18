package actiondata;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link SyncGameBoardDataRequestData} is the representation of data
 * to be used in a {@link Request} to display the game board.
 * 
 * Look on my works, ye mighty, and despair
 * 
 * @author Greg Schmitt
 *
 */
public class SyncGameBoardDataRequestData extends AbstractRequestActionData {
	private Integer food;
	private Integer fuel;
	private Collection<SyncGameBoardDataRequestData.LocationData> locations;
	private Collection<SyncGameBoardDataRequestData.PlayerData> players;
	private Collection<SyncGameBoardDataRequestData.VictoryData> victoryConditions;
	private Collection<SyncGameBoardDataRequestData.ItemData> communalInventory;
	
	/**
	 * Assemble the data for a DisplayBoardRequest.
	 * 
	 * @param food The current amount of food.
	 * @param fuel The current amount of fuel.
	 * @param locations The locations to display.
	 * @param players The players to display.
	 * @param victoryConditions The victory conditions.
	 */
	public SyncGameBoardDataRequestData(Integer food, Integer fuel,
								   Collection<SyncGameBoardDataRequestData.LocationData> locations, 
								   Collection<SyncGameBoardDataRequestData.PlayerData> players,
								   Collection<SyncGameBoardDataRequestData.VictoryData> victoryConditions,
								   Collection<SyncGameBoardDataRequestData.ItemData> communalInventory) {
		super(ActionData.SYNC_GAME_BOARD_DATA);
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
		for (SyncGameBoardDataRequestData.PlayerData player : this.players) {
			players.put(player.jsonify());
		}
		data.put(ActionData.PLAYERS, players);
		
		// Add locations to data
		JSONArray locations = new JSONArray();
		for (SyncGameBoardDataRequestData.LocationData location : this.locations) {
			locations.put(location.jsonify());
		}
		data.put(ActionData.LOCATIONS, locations);
		
		// Add victory conditions to data
		JSONArray victoryConditions = new JSONArray();
		for (SyncGameBoardDataRequestData.VictoryData victoryCondition : this.victoryConditions) {
			victoryConditions.put(victoryCondition.jsonify());
		}
		data.put(ActionData.VICTORY_CONDITIONS, victoryConditions);
		
		// Add communal inventory to data
		JSONArray communalInventory = new JSONArray();
		for (SyncGameBoardDataRequestData.ItemData item : this.communalInventory) {
			communalInventory.put(item.jsonify());
		}
		data.put(ActionData.COMMUNAL_INVENTORY, communalInventory);
		
		
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link SyncGameBoardDataRequestData} object.
	 * 
	 * @param jsonObj	the {@link JSONObject} to be parsed
	 * @return	the {@link SyncGameBoardDataRequestData} object parsed from JSON
	 * @throws JSONException
	 */
	public static SyncGameBoardDataRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		// Parse resources
		Integer food = jsonObj.getInt(ActionData.FOOD);
		Integer fuel = jsonObj.getInt(ActionData.FUEL);
		
		// Parse array of locations
		JSONArray locationArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		Collection<SyncGameBoardDataRequestData.LocationData> locations = new ArrayList<SyncGameBoardDataRequestData.LocationData>();
		JSONObject locationObject;
		SyncGameBoardDataRequestData.LocationData location;
		for (int i = 0; i < locationArray.length(); i++) {
			locationObject = locationArray.getJSONObject(i);
			location = new SyncGameBoardDataRequestData.LocationData(locationObject.getString(ActionData.MAP_LOCATION), 
																locationObject.getString(ActionData.TYPE));
			locations.add(location);
		}
		
		// Parse array of players
		JSONArray playerArray = jsonObj.getJSONArray(ActionData.PLAYERS);
		Collection<SyncGameBoardDataRequestData.PlayerData> players = new ArrayList<SyncGameBoardDataRequestData.PlayerData>();
		JSONObject playerObject;
		SyncGameBoardDataRequestData.PlayerData player;
		for (int i = 0; i < playerArray.length(); i++) {
			playerObject = playerArray.getJSONObject(i);
			player = new SyncGameBoardDataRequestData.PlayerData(playerObject.getString(ActionData.NAME), 
															Color.getColor(playerObject.getString(ActionData.COLOR)), 
															playerObject.getInt(ActionData.MAP_LOCATION));
			players.add(player);
		}
		
		// Parse array of victory conditions
		JSONArray victoryArray = jsonObj.getJSONArray(ActionData.VICTORY_CONDITIONS);
		Collection<SyncGameBoardDataRequestData.VictoryData> victories = new ArrayList<SyncGameBoardDataRequestData.VictoryData>();
		JSONObject victoryObject;
		SyncGameBoardDataRequestData.VictoryData victory;
		for (int i = 0; i < victoryArray.length(); i++) {
			victoryObject = victoryArray.getJSONObject(i);
			victory = new SyncGameBoardDataRequestData.VictoryData(victoryObject.getString(ActionData.NAME), 
															  victoryObject.getInt(ActionData.CURRENT_VALUE), 
															  victoryObject.getInt(ActionData.MAX_VALUE));
			victories.add(victory);
		}
		
		// Parse array of items (communalInventory)
		JSONArray communalInventoryArray = jsonObj.getJSONArray(ActionData.COMMUNAL_INVENTORY);
		Collection<SyncGameBoardDataRequestData.ItemData> communalInventory = new ArrayList<SyncGameBoardDataRequestData.ItemData>();
		JSONObject itemObject;
		SyncGameBoardDataRequestData.ItemData item;
		for (int i = 0; i < communalInventoryArray.length(); i++) {
			itemObject = communalInventoryArray.getJSONObject(i);
			item = new SyncGameBoardDataRequestData.ItemData(itemObject.getString(ActionData.NAME));
			communalInventory.add(item);
		}
		
		// Construct and return
		return new SyncGameBoardDataRequestData(food, fuel, locations, players, victories, communalInventory);
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
		private final String mapLocation;
		private final String type;

		public LocationData(final String mapLocation, final String type) {
			this.mapLocation = mapLocation;
			this.type = type;
		}
		
		public String getMapLocation() {
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
			if (other instanceof SyncGameBoardDataRequestData.LocationData) {
				SyncGameBoardDataRequestData.LocationData otherLocationData = (SyncGameBoardDataRequestData.LocationData) other;
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
			if (other instanceof SyncGameBoardDataRequestData.VictoryData) {
				SyncGameBoardDataRequestData.VictoryData otherVictoryData = (SyncGameBoardDataRequestData.VictoryData) other;
				return otherVictoryData.conditionName.equals(this.conditionName) &&
					   otherVictoryData.currentValue.equals(this.currentValue) &&
					   otherVictoryData.maxValue.equals(this.maxValue);
			} else {
				return false;
			}
		}
	}
	// =====================================
	
	// ===== ITEM DATA INNER CLASS =====
	public static class ItemData {
		private final String name;
		
		public ItemData(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		/**
		 * @return	{@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.NAME, this.name);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof SyncGameBoardDataRequestData.ItemData) {
				SyncGameBoardDataRequestData.ItemData otherItem = (SyncGameBoardDataRequestData.ItemData) other;
				return otherItem.name.equals(this.name);
			} else {
				return false;
			}
		}
	}
	// =================================
}
