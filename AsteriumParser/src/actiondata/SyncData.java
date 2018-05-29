package actiondata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import actiondata.SyncData.LocationData.LocationType;


public class SyncData {
	// ===== LOCATION DATA INNER CLASS =====
	/**
	 * {@link LocationData} is the representation of a location for the purposes of
	 * displaying the board.
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
			CONTROL_ROOM("control_room"), 
			MED_BAY("med_bay"), 
			MESS_HALL("mess_hall"), 
			RESIDENTIAL("residential"), 
			ARMORY("armory"), 
			LIBRARY("library"), 
			VEHICLE_BAY("vehicle_bay"), 
			ENGINE_ROOM("engine_room"),
			HYDROPONICS("hydroponics_bay"),
			RESEARCH_LAB("research_lab");

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
		
		public Integer getPosition() {
			return Integer.valueOf(this.mapLocation);
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

	// ===== ITEM DATA INNER CLASS =====
	public static class ItemData {
		private final String name;
		private final String description;
		private final String flavorText;
		private final String imagePath;
		private final boolean isLocationItem;
		private final Collection<LocationType> useLocations;

		public ItemData(final String name, final String description, final String flavor, final String image,
				final boolean isLocationItem, final Collection<LocationType> locations) {
			this.name = name;
			this.description = description;
			this.flavorText = flavor;
			this.imagePath = image;
			this.isLocationItem = isLocationItem;
			this.useLocations = locations;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.ITEM_NAME, this.name);
			data.put(ActionData.ITEM_DESC, this.description);
			data.put(ActionData.ITEM_FLAVOR_TEXT, this.flavorText);
			data.put(ActionData.ITEM_IMG, this.imagePath);
			data.put(ActionData.IS_LOCATION_ITEM, this.isLocationItem);
			JSONArray use = new JSONArray();
			for (LocationType type : this.useLocations) {
				use.put(type.toString().toString());
			}
			data.put(ActionData.USE_LOCATIONS, use);

			return data;
		}

		public static ItemData parseArgs(final JSONObject jsonObj) {
			String name = jsonObj.getString(ActionData.ITEM_NAME);
			String desc = jsonObj.getString(ActionData.ITEM_DESC);
			String flavor = jsonObj.getString(ActionData.ITEM_FLAVOR_TEXT);
			String img = jsonObj.getString(ActionData.ITEM_IMG);
			boolean isLocItem = jsonObj.getBoolean(ActionData.IS_LOCATION_ITEM);
			Collection<LocationType> locs = new ArrayList<LocationType>();
			JSONArray locArray = jsonObj.getJSONArray(ActionData.USE_LOCATIONS);
			for (int i = 0; i < locArray.length(); i++) {
				String type = locArray.get(i).toString();
				locs.add(LocationType.valueOf(type));
			}

			return new ItemData(name, desc, flavor, img, isLocItem, locs);
		}
	}
	// =================================
}
