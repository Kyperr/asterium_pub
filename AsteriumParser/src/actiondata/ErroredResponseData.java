package actiondata;

import org.json.JSONObject;

/**
 * {@link ErroredResponseData} is the representation of data
 * to be used in a {@link Response} to an action that erred.
 * 
 * @author Studio Toozo
 *
 */
public class ErroredResponseData extends AbstractResponseActionData{
	public ErroredResponseData(final String erroredActionName) {
		super(erroredActionName);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
}
