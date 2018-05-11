package actiondata;

import org.json.JSONObject;

public class AllocateStatsResponseData extends AbstractResponseActionData {

	public AllocateStatsResponseData() {
		super(ActionData.ALLOCATE_STATS);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}

}
