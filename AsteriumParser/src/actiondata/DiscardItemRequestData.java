package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.InventoryData.ItemData;
import message.Request;

/**
 * {@link DiscardItemRequestData} is the representation of data to be used in a
 * {@link Request} from a PlayerCharacter to use an Item from an Inventory.
 * 
 * @author Studio Toozo
 */
public class DiscardItemRequestData extends AbstractRequestActionData {
	private ItemData item;

	public DiscardItemRequestData(final ItemData item) {
		super(ActionData.DISCARD_ITEM);
		this.item = item;
	}

	public final ItemData getItem() {
		return this.item;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.ITEM, this.item.jsonify());

		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link DiscardItemRequestData} object
	 * 
	 * @param jsonObj
	 *            the {@link JSONObject} to be parsed
	 * @return the {@link DiscardItemRequestData} object parsed from JSON.
	 * @throws JSONException
	 */
	public static DiscardItemRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		JSONObject itemObject = jsonObj.getJSONObject(ActionData.ITEM);
		String itemID = itemObject.getString(ActionData.ITEM_NAME);
		ItemData item = new ItemData(itemID);

		return new DiscardItemRequestData(item);
	}
}
