package actiondata;

import org.json.JSONObject;

/**
 * ErroredResponseData is the representation of data
 * to be used in a Response to an action that erred.
 * 
 * @author Bridgette Campbell, Jenna Hand, Daniel McBride, and Greg Schmitt 
 *
 */
public class ErroredResponseData extends AbstractActionData{
	public ErroredResponseData(String erroredActionName) {
		super(erroredActionName);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}

	@Override
	protected boolean fieldsEqual(Object other) {
		return true;
	}
}
