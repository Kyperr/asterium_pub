package actiondata;

import org.json.JSONObject;

public class AllocateStatsResponseData extends AbstractResponseActionData {

	public AllocateStatsResponseData() {
		super(ALLOCATE_STATS_RESPONSE);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}

}
