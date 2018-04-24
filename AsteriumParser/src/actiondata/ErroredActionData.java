package actiondata;

import org.json.JSONObject;

public class ErroredActionData extends AbstractActionData{
	public ErroredActionData(String erroredActionName) {
		super(erroredActionName);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
}
