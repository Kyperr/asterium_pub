package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Response;

/**
 * {@link ToggleReadyUpResponseData} is the representation of data to be used in a
 * {@link Response} to the {@link Request} to ready up as a player.
 * 
 * @author Studio Toozo
 */
public class ToggleReadyUpResponseData extends AbstractResponseActionData {

	private static final String PLAYER_IS_READY = "player_is_ready";

	private final boolean playerIsReady;

	public ToggleReadyUpResponseData(boolean playerIsReady) {
		super(TOGGLE_READY_UP);
		this.playerIsReady = playerIsReady;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		data.put(PLAYER_IS_READY, this.playerIsReady);
		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link ToggleReadyUpResponseData} object
	 * 
	 * @param jsonObj
	 *            the {@link JSONObject} to be parsed
	 * @return the {@link ToggleReadyUpResponseData} object parsed from JSON.
	 * @throws JSONException
	 */
	public static ToggleReadyUpResponseData parseArgs(final JSONObject jsonObj) {
		boolean playerIsReady = jsonObj.getBoolean(PLAYER_IS_READY);
		return new ToggleReadyUpResponseData(playerIsReady);
	}

}
