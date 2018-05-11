package actiondata;

import org.json.JSONObject;

public class TurnResponseData extends AbstractResponseActionData {

	public TurnResponseData() {
		super(ActionData.TURN);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	public static TurnResponseData parseArgs(final JSONObject jsonObj) {
		return new TurnResponseData();
	}

}
