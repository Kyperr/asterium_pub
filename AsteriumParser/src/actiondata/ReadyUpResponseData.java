package actiondata;

import org.json.JSONObject;

public class ReadyUpResponseData extends AbstractResponseActionData{

	public ReadyUpResponseData() {
		super(READY_UP);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	public static ReadyUpResponseData parseArgs(final JSONObject jsonObj) {
		return new ReadyUpResponseData();
	}

}