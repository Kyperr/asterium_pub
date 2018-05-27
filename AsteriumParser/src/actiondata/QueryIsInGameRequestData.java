package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class QueryIsInGameRequestData extends AbstractRequestActionData {
	
	public QueryIsInGameRequestData() {
		super(ActionData.QUERY_IS_IN_GAME);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}

	public static QueryIsInGameRequestData parseArgs(final JSONObject jsonObj) throws JSONException { 
		//Construct and return
		return new QueryIsInGameRequestData();
	}

}