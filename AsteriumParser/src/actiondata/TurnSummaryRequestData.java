package actiondata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TurnSummaryRequestData extends AbstractRequestActionData {
	private List<String> turnSummary;
	
	public TurnSummaryRequestData(List<String> turnSummary) {
		super(ActionData.SUMMARY);
		
		this.turnSummary = new ArrayList<String>(turnSummary);
	}

	public List<String> getSummary() {
		return new ArrayList<String>(this.turnSummary);
	}
	
	@Override
	public JSONObject jsonify() {
		JSONObject result = new JSONObject();
		JSONArray summary = new JSONArray();
		
		summary.put(this.turnSummary);
		
		result.put(ActionData.SUMMARY, summary);
		return result;
	}
	
	public TurnSummaryRequestData parseArgs(JSONObject jsonObj) throws JSONException {
		JSONArray summary = jsonObj.getJSONArray(ActionData.SUMMARY);
		
		List<String> messageList = new ArrayList<String>();
		
		for (int i = 0; i < summary.length(); i++) {
			messageList.add(summary.getString(i));
		}
		
		return new TurnSummaryRequestData(messageList);
	}

}
