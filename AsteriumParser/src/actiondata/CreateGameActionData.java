package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateGameActionData extends AbstractActionData {

	public CreateGameActionData() {
		super(CREATE_GAME);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	/**
	 * Parses JSONObject into a {@link CreateGameActionData} object.
	 * 
	 * @param jsonObj	the JSONObject to be parsed
	 * @return	the CreateGameActionData object parsed from JSON
	 * @throws JSONException
	 */
	public static CreateGameActionData parseArgs(final JSONObject jsonObj) throws JSONException {
		// Construct and return
		return new CreateGameActionData();

	}

	@Override
	protected boolean fieldsEqual(final Object other) {
		return true;
	}
	
	
}
