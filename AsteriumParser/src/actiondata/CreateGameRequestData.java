package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * CreateGameRequestData is the representation of data
 * to be used in a Request to create a game.
 * 
 * @author Bridgette Campbell, Jenna Hand, Daniel McBride, and Greg Schmitt
 *
 */
public class CreateGameRequestData extends AbstractActionData {

	public CreateGameRequestData() {
		super(CREATE_GAME);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	/**
	 * Parses JSONObject into a {@link CreateGameRequestData} object.
	 * 
	 * @param jsonObj	the JSONObject to be parsed
	 * @return	the CreateGameActionData object parsed from JSON
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
