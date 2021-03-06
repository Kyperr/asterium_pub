package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.InventoryData.ItemData;
import message.Request;

/**
 * {@link CommunalInventoryRequestData} is the representation of data to be used in a
 * {@link Request} from a PlayerCharacter to use an Item from an Inventory.
 * 
 * @author Studio Toozo
 */
public class CommunalInventoryRequestData extends AbstractRequestActionData {
	private ItemData item;
	private boolean personalToCommunal;

	public CommunalInventoryRequestData(final ItemData item, final boolean personalToCommunal) {
		super(ActionData.COMMUNAL_INVENTORY);
		this.item = item;
		this.personalToCommunal = personalToCommunal;
	}

	public final ItemData getItem() {
		return this.item;
	}

	public final boolean personalToCommunal() {
		return this.personalToCommunal;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.ITEM, this.item.jsonify());

		data.put(ActionData.PERSONAL_TO_COMMUNAL, this.personalToCommunal);

		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link CommunalInventoryRequestData} object
	 * 
	 * @param jsonObj
	 *            the {@link JSONObject} to be parsed
	 * @return the {@link CommunalInventoryRequestData} object parsed from JSON.
	 * @throws JSONException
	 */
	public static CommunalInventoryRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		JSONObject itemObject = jsonObj.getJSONObject(ActionData.ITEM);
		String itemID = itemObject.getString(ActionData.ITEM_NAME);
		ItemData item = new ItemData(itemID);

		boolean personalToCommunal = jsonObj.getBoolean(ActionData.PERSONAL_TO_COMMUNAL);

		return new CommunalInventoryRequestData(item, personalToCommunal);
	}
	
}
