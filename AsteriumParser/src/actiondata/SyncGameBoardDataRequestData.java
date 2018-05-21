package actiondata;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncGameBoardDataRequestData.LocationData.LocationType;
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
	private int food;
	private int fuel;
	private int day;
	private Collection<SyncGameBoardDataRequestData.LocationData> locations;
	private Collection<SyncGameBoardDataRequestData.PlayerCharacterData> players;
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
	public SyncGameBoardDataRequestData(final Integer food, final Integer fuel, final Integer day, 
			final Collection<SyncGameBoardDataRequestData.LocationData> locations, 
			final Collection<SyncGameBoardDataRequestData.PlayerCharacterData> players,
			final Collection<SyncGameBoardDataRequestData.VictoryData> victoryConditions,
			final Collection<SyncGameBoardDataRequestData.ItemData> communalInventory) {
		super(ActionData.SYNC_GAME_BOARD_DATA);
		this.food = food;
		this.fuel = fuel;
		this.locations = locations;
		this.players = players;
		this.victoryConditions = victoryConditions;
		this.communalInventory = communalInventory;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		
		// Add resources to data
		data.put(ActionData.FOOD, this.food);
		data.put(ActionData.FUEL, this.fuel);
		data.put(ActionData.DAY, this.day);
		
		// Add players to data
		JSONArray players = new JSONArray();
		for (SyncGameBoardDataRequestData.PlayerCharacterData player : this.players) {
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
		Integer day = jsonObj.getInt(ActionData.DAY);
		
		// Parse array of locations
		
		JSONArray locationsArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		List<LocationData> locations = new ArrayList<LocationData>();
		for (int i = 0; i < locationsArray.length(); i++) {
			JSONObject locationObject = locationsArray.getJSONObject(i);
			String mapLocation = locationObject.getString(ActionData.MAP_LOCATION);
			String name = locationObject.getString(ActionData.LOCATION_NAME);
			LocationType type;
			String typeObject = locationObject.getString(ActionData.LOCATION_TYPE);
			type = LocationType.valueOf(typeObject);
			Set<String> activities = new HashSet<String>();
			JSONArray activitiesArray  = locationObject.getJSONArray(ActionData.ACTIVITIES);
			for (int j = 0; j < activitiesArray.length(); j++) {
				activities.add(activitiesArray.getString(j));	
			}
			
			locations.add(new LocationData(mapLocation, name, type, activities));
		}
		
		// Parse array of players
		JSONArray playerArray = jsonObj.getJSONArray(ActionData.PLAYERS);
		Collection<SyncGameBoardDataRequestData.PlayerCharacterData> players = new ArrayList<SyncGameBoardDataRequestData.PlayerCharacterData>();
		JSONObject playerObject;
		SyncGameBoardDataRequestData.PlayerCharacterData player;
		for (int i = 0; i < playerArray.length(); i++) {
			playerObject = playerArray.getJSONObject(i);
			player = new SyncGameBoardDataRequestData.PlayerCharacterData(playerObject.getString(ActionData.NAME), 
															Color.getColor(playerObject.getString(ActionData.COLOR)), 
															playerObject.getString(ActionData.MAP_LOCATION));
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
		return new SyncGameBoardDataRequestData(food, fuel, day, locations, players, victories, communalInventory);
	}
	
	public Integer getFood() {
		return food;
	}
	
	public void setFood(Integer food) {
		this.food = food;
	}
	
	public Integer getFuel() {
		return fuel;
	}
	
	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public void setSay(Integer day) {
		this.day = day;
	}
	
	public Collection<SyncGameBoardDataRequestData.ItemData> getCommunalInventory() {
		return communalInventory;
	}

	public void setCommunalInventory(Collection<SyncGameBoardDataRequestData.ItemData> communalInventory) {
		this.communalInventory = communalInventory;
	}
	
	public Collection<SyncGameBoardDataRequestData.LocationData> getLocations() {
		return locations;
	}
	
	public void setLocations(Collection<SyncGameBoardDataRequestData.LocationData> locations) {
		this.locations = locations;
	}
	
	public Collection<SyncGameBoardDataRequestData.VictoryData> getVictoryConditions() {
		return victoryConditions;
	}
	
	public void setVictoryConditions(Collection<SyncGameBoardDataRequestData.VictoryData> victoryConditions) {
		this.victoryConditions = victoryConditions;
	}
	
	public Collection<SyncGameBoardDataRequestData.PlayerCharacterData> getPlayers() {
		return players;
	}
	
	public void setPlayers(Collection<SyncGameBoardDataRequestData.PlayerCharacterData> players) {
		this.players = players;
	}

	// ===== PLAYER DATA INNER CLASS =====
	/**
	 * {@link PlayerCharacterData} is the representation of a player
	 * for the purposes of displaying the board.
	 * 
	 * @author Greg Schmitt
	 *
	 */
	public static class PlayerCharacterData {
		private final String name;
		private final Color color;
		private final String mapLocation;

		public PlayerCharacterData(final String name, final Color color, final String location) {
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
		
		public String getLocation() {
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
			if (other instanceof PlayerCharacterData) {
				PlayerCharacterData otherPlayerData = (PlayerCharacterData) other;
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
	 * @author Studio Toozo
	 *
	 */
	public static class LocationData {
		private final String mapLocation;
		private final String name;
		private final LocationType type;
		private Set<String> activities;

		public enum LocationType {
			CONTROL_ROOM("control_room"), MED_BAY("med_bay");

			private final String jsonVersion;

			LocationType(String jsonVersion) {
				this.jsonVersion = jsonVersion;
			}

			public String getJSONVersion() {
				return this.jsonVersion;
			}
		}
		
		public String getName() {
			return this.name;
		}
		
		public LocationType getType() {
			return this.type;
		}
		
		public LocationData(final String locationID, final String name, final LocationType type,
				final Set<String> activities) {
			this.mapLocation = locationID;
			this.name = name;
			this.type = type;
			this.activities = activities;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.MAP_LOCATION, this.mapLocation);
			data.put(ActionData.LOCATION_NAME, this.name);
			data.put(ActionData.LOCATION_TYPE, this.type.toString());
			JSONArray array = new JSONArray();

			for (String s : this.activities) {
				array.put(s);
			}
			data.put(ActionData.ACTIVITIES, array);

			return data;
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
		private final double currentValue;
		private final double maxValue;

		public VictoryData(final String conditionName, final double currentValue, final double maxValue) {
			this.conditionName = conditionName;
			this.currentValue = currentValue;
			this.maxValue = maxValue;
		}
		
		public String getName() {
			return this.conditionName;
		}
		
		public double getCurrentValue() {
			return this.currentValue;
		}
		
		public double getMaxValue() {
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
					   otherVictoryData.currentValue == this.currentValue &&
					   otherVictoryData.maxValue == this.maxValue;
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
