package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link ItemTurnRequestData} is the representation of data to 
 * be used in a {@link Request} from a player to take a turn.
 * 
 * @author Studio Toozo
 */
public class ItemTurnRequestData extends AbstractRequestActionData {

	private String locationID;
	private String itemName;

	public ItemTurnRequestData(final String locationID, final String itemName) {
		super(ActionData.ITEM_TURN_ACTION);
		this.locationID = locationID;
		this.itemName = itemName;
	}

	public String getActivityName() {
		return this.itemName;
	}
	
	public String getLocationID() {
		return this.locationID;
	}


	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.MAP_LOCATION, this.locationID);

		data.put(ActionData.ITEM_NAME, this.itemName);

		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link ItemTurnRequestData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link ItemTurnRequestData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static ItemTurnRequestData parseArgs(final JSONObject jsonObj) throws JSONException {

		// Get Location object
		String roomID = jsonObj.getString(ActionData.MAP_LOCATION);

		// Get activity name
		String itemName = jsonObj.getString(ActionData.ITEM_NAME);


		// Construct and return
		return new ItemTurnRequestData(roomID, itemName);

	}
}
