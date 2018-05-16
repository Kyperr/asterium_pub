package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.JoinAsPlayerRequestData.PlayerData;

public class QueryIsInGameResponseData extends AbstractRequestActionData {
	
	//The field.
	private static final String IS_IN_GAME = "is_in_game";
	
	private final boolean isInGame;
	
	public QueryIsInGameResponseData(boolean isInGame) {
		super(ActionData.QUERY_IS_IN_GAME);
		this.isInGame = isInGame;
	}

	@Override
	public JSONObject jsonify() {

		JSONObject data = new JSONObject();
		
		data.put(IS_IN_GAME, isInGame);

		return data;
	}


	public static QueryIsInGameResponseData parseArgs(final JSONObject jsonObj) throws JSONException { 
				
		boolean isInGame = jsonObj.getBoolean(IS_IN_GAME);
		
		//Construct and return
		return new QueryIsInGameResponseData(isInGame);

	}

}
