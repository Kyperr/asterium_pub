package actiondata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncData.ItemData;
import actiondata.SyncData.LocationData;
import actiondata.SyncData.LocationData.LocationType;
import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData;
import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType;
import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData;
import message.Request;

/**
 * {@link SyncPlayerClientDataRequestData} is the representation of data to be
 * used in a {@link Request} to the PlayerClient to update the data.
 * 
 * @author Studio Toozo
 */
public class SyncPlayerClientDataRequestData extends AbstractRequestActionData {

	private int food = 0;
	private int fuel = 0;
	private int day = 0;
	private boolean youWon = false;
	private List<LocationData> locations;
	private SyncPlayerClientDataRequestData.PlayerCharacterData yourCharacter;
	private Collection<String> characterNames;
	private List<ItemData> inventory;
	private String gamePhaseName;

	public SyncPlayerClientDataRequestData(final int food, 
										   final int fuel, 
										   final int day,
										   final boolean youWon,
										   final List<LocationData> locations, 
										   final PlayerCharacterData you, 
										   final Collection<String> characterNames, 
										   final String gamePhaseName,
										   final List<ItemData> inventory) {
		super(ActionData.SYNC_PLAYER_CLIENT_DATA);
		this.food = food;
		this.fuel = fuel;
		this.day = day;
		this.youWon = youWon;
		this.locations = locations;
		this.yourCharacter = you;
		this.characterNames = characterNames;
		this.inventory = inventory;
		this.gamePhaseName = gamePhaseName;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();

		for (LocationData datum : this.locations) {
			array.put(datum.jsonify());
		}

		data.put(ActionData.FOOD, this.food);
		data.put(ActionData.FUEL, this.fuel);
		data.put(ActionData.DAY, this.day);
		data.put(ActionData.YOU_WON, youWon);
		data.put(ActionData.LOCATIONS, array);
		data.put(ActionData.CHARACTER, this.yourCharacter.jsonify());
		JSONArray charactersArray = new JSONArray();
		for (String s : this.characterNames) {
			charactersArray.put(s);
		}
		data.put(ActionData.CHARACTERS, charactersArray);
		JSONArray inventoryArray = new JSONArray();
		for (ItemData item : this.inventory) {
			inventoryArray.put(item.jsonify());
		}
		data.put(ActionData.COMMUNAL_INVENTORY, inventoryArray);
		data.put(ActionData.GAME_PHASE_NAME, this.gamePhaseName);
		return data;
	}

	/**
	 * Parses JSONObject into a {@link SyncPlayerClientDataRequestData} object.
	 * 
	 * @param jsonObj
	 *            the JSONObject to be parsed
	 * @return the SyncPlayerClientDataRequestData object parsed from JSON
	 * @throws JSONException
	 */
	public static SyncPlayerClientDataRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		int food = jsonObj.getInt(ActionData.FOOD);
		int fuel = jsonObj.getInt(ActionData.FUEL);
		int day = jsonObj.getInt(ActionData.DAY);
		boolean youWon = jsonObj.getBoolean(ActionData.YOU_WON);
		
		// build character
		PlayerCharacterData character;
		JSONObject characterObj = jsonObj.getJSONObject(ActionData.CHARACTER);
		String characterName = characterObj.getString(ActionData.CHARACTER_NAME);
		boolean isParasite = characterObj.getBoolean(ActionData.IS_PARASITE);
		// character stats
		JSONObject statsObj = characterObj.getJSONObject(ActionData.STATS);
		Integer health = statsObj.getInt(ActionData.HEALTH);
		Integer stamina = statsObj.getInt(ActionData.STAMINA);
		Integer luck = statsObj.getInt(ActionData.LUCK);
		Integer intuition = statsObj.getInt(ActionData.INTUITION);
		StatsData stats = new StatsData(health, stamina, luck, intuition);
		
		double exposure = characterObj.getDouble(ActionData.EXPOSURE);
		
		Collection<String> characters = new ArrayList<String>();
		JSONArray charactersArray = jsonObj.getJSONArray(ActionData.CHARACTERS);
		for (int c = 0; c < characters.size(); c++) {
			characters.add(charactersArray.getString(c));
		}

		// character personal inventory
		JSONArray personalInvArray = characterObj.getJSONArray(ActionData.PERSONAL_INVENTORY);
		List<ItemData> personalInv = new ArrayList<ItemData>();
		JSONObject personalInvObject;
		ItemData personalInvItem;
		for (int g = 0; g < personalInvArray.length(); g++) {
			personalInvObject = personalInvArray.getJSONObject(g);
			personalInvItem = ItemData.parseArgs(personalInvObject);
			personalInv.add(personalInvItem);
		}

		// character loadout
		JSONArray loadoutArray = characterObj.getJSONArray(ActionData.LOADOUT);
		Map<EquipmentType, ItemData> equipments = new HashMap<EquipmentType, ItemData>();
		LoadoutData loadout;
		JSONObject equipmentObject;
		ItemData equipment;

		for (int i = 0; i < loadoutArray.length(); i++) {
			JSONObject jo = loadoutArray.getJSONObject(i);
			EquipmentType type = EquipmentType.valueOf(jo.getString(ActionData.SLOT));
			equipmentObject = jo.getJSONObject(ActionData.EQUIPMENT);
			equipment = ItemData.parseArgs(equipmentObject);
			equipments.put(type, equipment);
		}
		loadout = new LoadoutData(equipments);

		boolean turnTaken = characterObj.getBoolean(ActionData.TURN_TAKEN);
		boolean ready = characterObj.getBoolean(ActionData.READY);

		character = new PlayerCharacterData(characterName, 
											isParasite, 
											stats, 
											exposure,
											personalInv, 
											loadout, 
											turnTaken, 
											ready);

		// build locations
		JSONArray locationsArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		List<LocationData> locations = new ArrayList<LocationData>();
		for (int i = 0; i < locationsArray.length(); i++) {
			JSONObject locationObject = locationsArray.getJSONObject(i);
			String locationID = locationObject.getString(ActionData.MAP_LOCATION);
			String name = locationObject.getString(ActionData.LOCATION_NAME);
			LocationType type;
			JSONObject typeObject = locationObject.getJSONObject(ActionData.LOCATION_TYPE);
			type = LocationType.valueOf(typeObject.getString(ActionData.LOCATION_TYPE));
			Set<String> activities = new HashSet<String>();
			JSONArray activitiesArray = locationObject.getJSONArray(ActionData.ACTIVITIES);
			for (int j = 0; j < activitiesArray.length(); j++) {
				activities.add(activitiesArray.getString(j));
			}

			locations.add(new LocationData(locationID, name, type, activities));
		}

		// build communal inventory
		JSONArray inventoryArray = jsonObj.getJSONArray(ActionData.COMMUNAL_INVENTORY);
		List<ItemData> inventory = new ArrayList<ItemData>();
		JSONObject itemObject;
		ItemData item;
		for (int j = 0; j < inventoryArray.length(); j++) {
			itemObject = inventoryArray.getJSONObject(j);
			item = ItemData.parseArgs(itemObject);
			inventory.add(item);
		}

		String gamePhaseName = jsonObj.getString(ActionData.GAME_PHASE_NAME);
		return new SyncPlayerClientDataRequestData(food, fuel, day, youWon, locations, character, 
												   characters, gamePhaseName, inventory);
	}


	/**
	 * {@link PlayerCharacterData} is an inner class of
	 * {@link SyncPlayerClientDataRequestData} used only for the purpose of updating
	 * a player client.
	 * 
	 * @author Studio Toozo
	 */
	public static class PlayerCharacterData {

		private String name;
		private boolean isParasite;
		private StatsData stats;
		private double exposure;
		private List<ItemData> inventory;
		private LoadoutData equipped;
		private boolean turnTaken;
		private boolean ready;

		public PlayerCharacterData(final String characterName, 
								   final boolean isParasite,
								   final StatsData stats,
								   final double exposure,
								   final List<ItemData> inventory, 
								   final LoadoutData equipped, 
								   final boolean turnTaken, 
								   final boolean ready) {
			this.name = characterName;
			this.isParasite = isParasite;
			this.stats = stats;
			this.exposure = exposure;
			this.inventory = inventory;
			this.equipped = equipped;
			this.turnTaken = turnTaken;
			this.ready = ready;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.CHARACTER_NAME, this.name);
			data.put(ActionData.IS_PARASITE, this.isParasite);
			data.put(ActionData.STATS, this.stats.jsonify());
			data.put(ActionData.EXPOSURE, this.exposure);
			JSONArray inventoryArray = new JSONArray();
			for (ItemData item : this.inventory) {
				inventoryArray.put(item.jsonify());
			}
			data.put(ActionData.PERSONAL_INVENTORY, inventoryArray);
			data.put(ActionData.LOADOUT, this.equipped.jsonify());
			data.put(ActionData.TURN_TAKEN, this.turnTaken);
			data.put(ActionData.READY, this.ready);

			return data;
		}

		/**
		 * {@link StatsData} is an inner class of {@link PlayerCharacterData} used only
		 * for the purpose of updating a player client within a
		 * {@link SyncPlayerClientDataRequestData}.
		 * 
		 * @author Studio Toozo
		 */
		public static class StatsData {

			private Integer health;
			private Integer stamina;
			private Integer luck;
			private Integer intuition;

			public StatsData(final Integer health, final Integer stamina, final Integer luck, final Integer intuition) {
				this.health = health;
				this.stamina = stamina;
				this.luck = luck;
				this.intuition = intuition;
			}

			public JSONObject jsonify() {
				JSONObject data = new JSONObject();
				data.put(ActionData.HEALTH, this.health);
				data.put(ActionData.STAMINA, this.stamina);
				data.put(ActionData.LUCK, this.luck);
				data.put(ActionData.INTUITION, this.intuition);

				return data;
			}
		}

		public static class LoadoutData {
			private Map<EquipmentType, ItemData> equipment;

			public LoadoutData(final Map<EquipmentType, ItemData> equipment) {
				this.equipment = equipment;
			}

			public JSONObject jsonify() {
				JSONObject loadoutObject = new JSONObject();
				for (EquipmentType type : equipment.keySet()) {
					loadoutObject.put(type.toString(), this.equipment.get(type).jsonify());
				}
				return loadoutObject;
			}

			public enum EquipmentType {
				HEAD, TORSO, ARMS, LEGS;

				@Override
				public String toString() {
					return this.name();
				}

			}
		}
	}

	
}
