package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Response;

/**
 * {@link ReadyUpResponseData} is the representation of data to be used in a
 * {@link Response} to the {@link Request} to ready up as a player.
 * 
 * @author Studio Toozo
 */
public class ReadyUpResponseData extends AbstractResponseActionData {

	private static final String PLAYER_IS_READY = "player_is_ready";

	private final boolean playerIsReady;

	public ReadyUpResponseData(boolean playerIsReady) {
		super(READY_UP);
		this.playerIsReady = playerIsReady;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		data.put(PLAYER_IS_READY, this.playerIsReady);
		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link ReadyUpResponseData} object
	 * 
	 * @param jsonObj
	 *            the {@link JSONObject} to be parsed
	 * @return the {@link ReadyUpResponseData} object parsed from JSON.
	 * @throws JSONException
	 */
	public static ReadyUpResponseData parseArgs(final JSONObject jsonObj) {
		boolean playerIsReady = jsonObj.getBoolean(PLAYER_IS_READY);
		return new ReadyUpResponseData(playerIsReady);
	}

}
