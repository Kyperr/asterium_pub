package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class AllocateStatsRequestData extends AbstractRequestActionData {

	private static final String STAMINA = "stamina";
	private static final String LUCK = "luck";
	private static final String INTUITION = "intuition";
	
	private int stamina;
	private int luck;
	private int intuition;
	
	public AllocateStatsRequestData(final int stamina, final int luck, final int intuition) {
		super(ALLOCATE_STATS);
		this.stamina = stamina;
		this.luck = luck;
		this.intuition = intuition;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		
		data.put(STAMINA, this.stamina);		
		data.put(LUCK, this.luck);		
		data.put(INTUITION, this.intuition);		
		
		return data;
	}

	public int getStamina() {
		return this.stamina;
	}
	
	public int getLuck() {
		return this.luck;
	}
	
	public int getIntuition() {
		return this.intuition;
	}
	
	public static AllocateStatsRequestData parseArgs(JSONObject jsonObj) throws JSONException{
		
		jsonObj = jsonObj.getJSONObject(ActionData.STATS);

		int stamina = jsonObj.getInt(STAMINA);
		int luck = jsonObj.getInt(LUCK);
		int intuition = jsonObj.getInt(INTUITION);
		
		return new AllocateStatsRequestData(stamina, luck, intuition);
	}

}
