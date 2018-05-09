package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link CreateGameRequestData} is the representation of data
 * to be used in a {@link Request} to create a game.
 * 
 * @author Studio Toozo
 *
 */
public class CreateGameRequestData extends AbstractActionData {

	public CreateGameRequestData() {
		super(ActionData.CREATE_GAME);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link CreateGameRequestData} object.
	 * 
	 * @param jsonObj	the {@link JSONObject} to be parsed
	 * @return	the {@link CreateGameRequestData} object parsed from JSON
	 * @throws JSONException
	 */
	public static CreateGameRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		// Construct and return
		return new CreateGameRequestData();

	}

	@Override
	protected boolean fieldsEqual(final Object other) {
		return true;
	}
	
	
}
